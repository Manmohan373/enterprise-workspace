package com.enterprise.user.service;

import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.UpdateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.entity.Organization;
import com.enterprise.user.entity.OrganizationStatus;
import com.enterprise.user.exception.ConflictException;
import com.enterprise.user.exception.ResourceNotFoundException;
import com.enterprise.user.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

    private final OrganizationRepository repository;

    @Override
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {

        if(repository.existsByCode(request.code())) {
            throw new ConflictException("Organization code already exists.");
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
    public List<OrganizationResponse> getAll() {
        return List.of();
    }

    @Override
    public OrganizationResponse update(UUID id, UpdateOrganizationRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }



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
