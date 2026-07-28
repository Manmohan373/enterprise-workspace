package com.enterprise.user.controller;

import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.OrganizationUpdateRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.dto.response.PageResponse;
import com.enterprise.user.service.OrganizationService;
import com.enterprise.user.util.SearchRequestBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;


    @GetMapping("/search")
    public ApiResponse<SearchResponse<OrganizationResponse>> search(
        @RequestParam Map<String, String> params) {

        SearchRequest request = SearchRequestBuilder.from(params);

        return new ApiResponse<>(true,
            "Organizations fetched successfully.",
            organizationService.search(request)
        );
    }

    @GetMapping("/list")
    public ApiResponse<PageResponse<OrganizationResponse>> getOrganizations(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
        )
        Pageable pageable){
        return new ApiResponse<>(true,"Organizations fetched successfully",organizationService.getOrganizations(pageable));
    }

    @PostMapping("/register")
    public ApiResponse<OrganizationResponse> create(
        @Valid @RequestBody CreateOrganizationRequest request) {

        return new ApiResponse<>(true,"Organization created successfully",
            organizationService.createOrganization(request));
    }

    @GetMapping("getOrg/{id}")
    public ApiResponse<OrganizationResponse> getOrganization(
        @PathVariable UUID id
    ) {

        return new ApiResponse<>(true,
            "Organization fetched successfully",
            organizationService.getOrganization(id)
        );
    }

    @PutMapping("updateOrg/{id}")
    public ApiResponse<OrganizationResponse> updateOrganization(
        @PathVariable UUID id,
        @Valid @RequestBody OrganizationUpdateRequest request
    ) {

        return new ApiResponse<>(true,
            "Organization updated successfully",
            organizationService.updateOrganization(id, request)
        );
    }

    @DeleteMapping("deleteOrg/{id}")
    public ApiResponse<Void> deleteOrganization(
        @PathVariable UUID id
    ) {

        organizationService.deleteOrganization(id);

        return new ApiResponse<>( true,
            "Organization deleted successfully",
            null
        );
    }
}
