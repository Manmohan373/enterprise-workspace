package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.TeamCreateRequest;
import com.enterprise.user.dto.request.TeamUpdateRequest;
import com.enterprise.user.dto.response.TeamResponse;
import com.enterprise.user.entity.*;
import com.enterprise.user.exception.DuplicateResourceException;
import com.enterprise.user.exception.ResourceNotFoundException;
import com.enterprise.user.repository.DynamicSearchRepository;
import com.enterprise.user.repository.ProjectRepository;
import com.enterprise.user.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final ProjectRepository projectRepository;

    private final DynamicSearchRepository dynamicSearchRepository;

    @Override
    public TeamResponse createTeam(
        TeamCreateRequest request
    ) {

        Project project = getProject(
            request.projectId()
        );

        validateTeamCode(request.code());

        validateTeamName(
            request.name(),
            project.getId()
        );

        Team team = buildTeam(
            request,
            project
        );

        Team savedTeam =
            teamRepository.save(team);

        return toResponse(savedTeam);
    }

    @Override
    public TeamResponse getTeam(UUID id) {

        Team team = getTeamById(id);

        return toResponse(team);

    }

    private Team getTeamById(UUID id) {

        return teamRepository
            .findByIdAndStatusNot(
                id,
                TeamStatus.DELETED
            )
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Team not found."
                ));
    }

    @Override
    public TeamResponse updateTeam(
        UUID id,
        TeamUpdateRequest request
    ) {

        Team team = getTeamById(id);

        if (!team.getName().equals(request.name())) {

            validateTeamName(
                request.name(),
                team.getProject().getId()
            );

            team.setName(request.name());
        }

        team.setDescription(request.description());

        team.setStatus(request.status());

        team.setUpdatedAt(Instant.now());

        Team updatedTeam = teamRepository.save(team);

        return toResponse(updatedTeam);
    }

    @Override
    public void deleteTeam(UUID id) {

        Team team = getTeamById(id);

        if (team.getStatus() == TeamStatus.DELETED) {
            return;
        }

        team.setStatus(TeamStatus.DELETED);

        team.setUpdatedAt(Instant.now());

        teamRepository.save(team);

    }

    @Override
    public SearchResponse<TeamResponse> search(SearchRequest request) {
        SearchResponse<Team> result =
            dynamicSearchRepository.search(
                Team.class,
                request
            );

        List<TeamResponse> responses =
            result.data()
                .stream()
                .map(this::toResponse)
                .toList();

        return SearchResponse.<TeamResponse>builder()
            .data(responses)
            .page(result.page())
            .size(result.size())
            .totalElements(result.totalElements())
            .totalPages(result.totalPages())
            .first(result.first())
            .last(result.last())
            .build();
    }


    private Project getProject(UUID projectId) {

        return projectRepository
            .findByIdAndStatusNot(
                projectId,
                ProjectStatus.DELETED
            )
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Project not found."
                ));
    }

    private void validateTeamCode(String code) {
        if (teamRepository.existsByCode(code)) {
            throw new DuplicateResourceException("Team code already exists.");
        }
    }

    private void validateTeamName(
        String name,
        UUID projectId
    ) {
        if (teamRepository.existsByNameAndProjectId(
            name, projectId)) {
            throw new DuplicateResourceException(
                "Team name already exists in this project."
            );
        }
    }

    private Team buildTeam(
        TeamCreateRequest request,
        Project project
    ) {

        Instant now = Instant.now();

        return Team.builder()
            .id(UUID.randomUUID())
            .project(project)
            .name(request.name())
            .code(request.code())
            .description(request.description())
            .status(TeamStatus.ACTIVE)
            .createdAt(now)
            .updatedAt(now)
            .build();

    }

    private TeamResponse toResponse(Team team) {

        return new TeamResponse(
            team.getId(),
            team.getProject().getId(),
            team.getProject().getName(),
            team.getName(),
            team.getCode(),
            team.getDescription(),
            team.getStatus(),
            team.getCreatedAt(),
            team.getUpdatedAt()
        );

    }
}
