package com.enterprise.user.controller;

import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("/organizations")
    public ApiResponse<OrganizationResponse> create(
        @Valid @RequestBody CreateOrganizationRequest request) {

        return new ApiResponse<>(true,"Organization created successfully",
            organizationService.createOrganization(request));
    }

    @GetMapping("getOrganisation/{id}")
    public ApiResponse<OrganizationResponse> getOrganization(
        @PathVariable UUID id
    ) {

        return new ApiResponse<>(true,
            "Organization fetched successfully",
            organizationService.getOrganization(id)
        );
    }
}
