package com.enterprise.user.controller;


import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.TeamCreateRequest;
import com.enterprise.user.dto.request.TeamUpdateRequest;
import com.enterprise.user.dto.response.TeamResponse;
import com.enterprise.user.service.TeamService;
import com.enterprise.user.util.SearchRequestBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> create(
        @Valid @RequestBody TeamCreateRequest request) {

        TeamResponse response = teamService.createTeam(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true,
                "Team created successfully.",
                response
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> get(
        @PathVariable UUID id) {

        TeamResponse response = teamService.getTeam(id);

        return ResponseEntity.ok(
            new ApiResponse<>(true,"Team fetched successfully",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> update(
        @PathVariable UUID id,
        @Valid @RequestBody TeamUpdateRequest request) {

        TeamResponse response =
            teamService.updateTeam(id, request);

        return ResponseEntity.ok(
            new ApiResponse<>(true,
                "Team updated successfully.",
                response
            ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @PathVariable UUID id) {

        teamService.deleteTeam(id);

        return ResponseEntity.ok(
            new ApiResponse<>(true,
                "Team deleted successfully.",
                null
            )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SearchResponse<TeamResponse>>> search(
        @RequestParam Map<String, String> params) {

        SearchRequest request =
            SearchRequestBuilder.from(params);

        SearchResponse<TeamResponse> response =
            teamService.search(request);

        return ResponseEntity.ok(
            new ApiResponse<>(true,"Teams fetched successfully",response)
        );
    }
}
