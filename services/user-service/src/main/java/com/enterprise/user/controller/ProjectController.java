package com.enterprise.user.controller;

import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.ProjectCreateRequest;
import com.enterprise.user.dto.request.ProjectUpdateRequest;
import com.enterprise.user.dto.response.ProjectResponse;
import com.enterprise.user.service.ProjectService;
import com.enterprise.user.util.SearchRequestBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> create(
        @Valid @RequestBody ProjectCreateRequest request) {

        ProjectResponse response = projectService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true,
                "Project created successfully.",
                response
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> get(
        @PathVariable UUID id) {

        ProjectResponse response = projectService.get(id);

        return ResponseEntity.ok(
            new ApiResponse<>(true,"Project fetched successfully.",response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> update(
        @PathVariable UUID id,
        @Valid @RequestBody ProjectUpdateRequest request) {

        ProjectResponse response =
            projectService.update(id, request);

        return ResponseEntity.ok(
            new ApiResponse<>(true,
                "Project updated successfully.",
                response
            ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @PathVariable UUID id) {

        projectService.delete(id);

        return ResponseEntity.ok(
            new ApiResponse<>(true,
                "Project deleted successfully.",null
            )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SearchResponse<ProjectResponse>>> search(
        @RequestParam Map<String, String> params) {

        SearchRequest request =
            SearchRequestBuilder.from(params);

        SearchResponse<ProjectResponse> response =
            projectService.search(request);

        return ResponseEntity.ok(
            new ApiResponse<>(true,"Projects fetched successfully.",response)
        );
    }
}
