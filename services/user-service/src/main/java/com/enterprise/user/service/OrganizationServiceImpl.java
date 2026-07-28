package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.OrganizationUpdateRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.dto.response.PageResponse;
import com.enterprise.user.entity.Organization;
import com.enterprise.user.entity.OrganizationStatus;
import com.enterprise.user.exception.ConflictException;
import com.enterprise.user.exception.ResourceNotFoundException;
import com.enterprise.user.repository.DynamicSearchRepository;
import com.enterprise.user.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

    private final OrganizationRepository repository;

    private final DynamicSearchRepository searchRepository;

    @Override
    @Transactional
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {

        if(repository.existsByCode(request.code())) {
            throw new ConflictException("Organization code already exists.");
        }

        if (request.email() != null && repository.existsByEmail(request.email())) {
            throw new ConflictException("Organization email already exists.");
        }

        Organization organization = toEntity(request);
        Organization savedOrganization = repository.save(organization);

        return toResponse(savedOrganization);
    }

    @Override
    public OrganizationResponse getOrganization(UUID id) {
        Organization organization = repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Organization not found with id: " + id
                ));
        return toResponse(organization);
    }

    @Override
    public PageResponse<OrganizationResponse> getOrganizations(Pageable pageable) {

        int pageSize = Math.min(pageable.getPageSize(), 100);

        Pageable safePageable = PageRequest.of(
            pageable.getPageNumber(),
            pageSize,
            pageable.getSort()
        );

        Page<Organization> page = repository.findByStatusNot(
            OrganizationStatus.DELETED,
            safePageable
        );

        List<OrganizationResponse> organizations = page.getContent()
            .stream()
            .map(this::toResponse)
            .toList();

        return PageResponse.<OrganizationResponse>builder()
            .content(organizations)
            .page(page.getNumber())
            .size(page.getSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .first(page.isFirst())
            .last(page.isLast())
            .build();
    }

    @Override
    @Transactional
    public OrganizationResponse updateOrganization(
        UUID organizationId,
        OrganizationUpdateRequest request) {

        Organization organization = repository.findById(organizationId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Organization not found"));

        if (request.email() != null
            && repository.existsByEmailAndIdNot(
            request.email(), organizationId) && repository.existsByNameAndIdNot(
            request.name(), organizationId)) {

            throw new ConflictException(
                "Organization email already exists");
        }

        organization.setName(request.name());
        organization.setEmail(request.email());
        organization.setPhone(request.phone());
        organization.setWebsite(request.website());
        organization.setDescription(request.description());

        organization.setUpdatedAt(Instant.now());

        Organization updated =
            repository.save(organization);

        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteOrganization(UUID id) {
        Organization organization = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Organization not found"));

        if (organization.getStatus() == OrganizationStatus.DELETED) {
            throw new IllegalStateException("Organization is already deleted.");
        }

        organization.setStatus(OrganizationStatus.DELETED);
        repository.save(organization);
    }

    @Override
    public SearchResponse<OrganizationResponse> search(SearchRequest request) {

        SearchResponse<Organization> result = searchRepository.search(Organization.class,request);

        List<OrganizationResponse> organizations =
            result.data()
                .stream()
                .map(this::toResponse)
                .toList();

        return SearchResponse.<OrganizationResponse>builder()
            .data(organizations)
            .page(result.page())
            .size(result.size())
            .totalElements(result.totalElements())
            .totalPages(result.totalPages())
            .first(result.first())
            .last(result.last())
            .build();
    }


    //mapper methods
    public Organization toEntity(CreateOrganizationRequest request) {
        return Organization.builder()
            .name(request.name())
            .code(request.code().toUpperCase())
            .email(request.email())
            .phone(request.phone())
            .website(request.website())
            .description(request.description())
            .status(OrganizationStatus.ACTIVE)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .version(0L)
            .build();
    }

    public OrganizationResponse toResponse(Organization organization) {
        return OrganizationResponse.builder()
            .id(organization.getId())
            .name(organization.getName())
            .code(organization.getCode())
            .email(organization.getEmail())
            .phone(organization.getPhone())
            .website(organization.getWebsite())
            .description(organization.getDescription())
            .status(organization.getStatus())
            .createdAt(organization.getCreatedAt())
            .updatedAt(organization.getUpdatedAt())
            .build();
    }
}
