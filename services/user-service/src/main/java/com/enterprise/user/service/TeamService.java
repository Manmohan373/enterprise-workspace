package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.TeamCreateRequest;
import com.enterprise.user.dto.request.TeamUpdateRequest;
import com.enterprise.user.dto.response.TeamResponse;

import java.util.UUID;

public interface TeamService {
    TeamResponse createTeam(TeamCreateRequest request);

    TeamResponse getTeam(UUID id);

    TeamResponse updateTeam(
        UUID id,
        TeamUpdateRequest request
    );

    void deleteTeam(UUID id);

    SearchResponse<TeamResponse> search(
        SearchRequest request
    );
}
