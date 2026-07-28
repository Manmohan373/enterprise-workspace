package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.ProjectCreateRequest;
import com.enterprise.user.dto.request.ProjectUpdateRequest;
import com.enterprise.user.dto.response.ProjectResponse;
import com.enterprise.user.entity.Organization;
import com.enterprise.user.entity.Project;
import com.enterprise.user.entity.ProjectStatus;
import com.enterprise.user.exception.BadRequestException;
import com.enterprise.user.exception.ResourceNotFoundException;
import com.enterprise.user.repository.DynamicSearchRepository;
import com.enterprise.user.repository.OrganizationRepository;
import com.enterprise.user.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final OrganizationRepository organizationRepository;

    private final DynamicSearchRepository dynamicSearchRepository;

    @Override
    public ProjectResponse create(ProjectCreateRequest request) {

        Organization organization = getOrganization(request.organizationId());

        validateProjectCode(request.code());

        validateProjectName(request.name(), organization.getId());

        validateProjectDates(request.startDate(), request.endDate());

        Project project = buildProject(request, organization);

        project = projectRepository.save(project);

        return toResponse(project);
    }

    private Organization getOrganization(UUID organizationId) {

        return organizationRepository.findById(organizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Organization not found with id : " + organizationId
                ));
    }

    private void validateProjectCode(String code) {

        if (projectRepository.existsByCode(code)) {
            throw new BadRequestException(
                "Project code already exists : " + code
            );
        }
    }

    private void validateProjectName(String name,
                                     UUID organizationId) {

        if (projectRepository.existsByNameAndOrganizationId(
            name,
            organizationId)) {

            throw new BadRequestException(
                "Project name already exists in organization : " + name
            );
        }
    }

    private void validateProjectDates(Instant startDate,
                                      Instant endDate) {

        if (startDate != null
            && endDate != null
            && endDate.isBefore(startDate)) {

            throw new BadRequestException(
                "End date cannot be before start date."
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse get(UUID id) {

        Project project = getProject(id);

        return toResponse(project);
    }

    @Override
    public ProjectResponse update(UUID id,
                                  ProjectUpdateRequest request) {

        Project project = getProject(id);

        if (!project.getName().equals(request.name())) {

            validateProjectName(
                request.name(),
                project.getOrganization().getId()
            );

            project.setName(request.name());
        }

        validateProjectDates(
            request.startDate(),
            request.endDate()
        );

        project.setClientName(request.clientName());
        project.setDescription(request.description());
        project.setProjectType(request.projectType());
        project.setStatus(request.status());
        project.setStartDate(request.startDate());
        project.setEndDate(request.endDate());
        project.setUpdatedAt(Instant.now());

        project = projectRepository.save(project);

        return toResponse(project);
    }

    @Override
    public void delete(UUID id) {

        Project project = getProject(id);

        project.setStatus(ProjectStatus.DELETED);
        project.setUpdatedAt(Instant.now());

        projectRepository.save(project);
    }


    @Override
    @Transactional(readOnly = true)
    public SearchResponse<ProjectResponse> search(SearchRequest request) {

        SearchResponse<Project> result =
            dynamicSearchRepository.search(Project.class, request);

        List<ProjectResponse> responses = result.data()
            .stream()
            .map(this::toResponse)
            .toList();

        return SearchResponse.<ProjectResponse>builder()
            .data(responses)
            .page(result.page())
            .size(result.size())
            .totalElements(result.totalElements())
            .totalPages(result.totalPages())
            .first(result.first())
            .last(result.last())
            .build();
    }

    private Project getProject(UUID id) {

        return projectRepository
            .findByIdAndStatusNot(id, ProjectStatus.DELETED)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Project not found with id : " + id
                ));
    }

    private Project buildProject(ProjectCreateRequest request,
                                 Organization organization) {

        Instant now = Instant.now();

        return Project.builder()
            .id(UUID.randomUUID())
            .organization(organization)
            .name(request.name())
            .code(request.code())
            .clientName(request.clientName())
            .description(request.description())
            .projectType(request.projectType())
            .status(ProjectStatus.ACTIVE)
            .startDate(request.startDate())
            .endDate(request.endDate())
            .createdAt(now)
            .updatedAt(now)
            .version(0L)
            .build();
    }

    private ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
            project.getId(),
            project.getOrganization().getId(),
            project.getOrganization().getName(),
            project.getName(),
            project.getCode(),
            project.getClientName(),
            project.getDescription(),
            project.getProjectType(),
            project.getStatus(),
            project.getStartDate(),
            project.getEndDate(),
            project.getCreatedAt(),
            project.getUpdatedAt()
        );
    }
}
