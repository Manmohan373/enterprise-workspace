package com.enterprise.user.controller;

import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class OrganizationController {

    private OrganizationService organizationService;

    @PostMapping
    public ApiResponse<OrganizationResponse> create(
        @Valid @RequestBody CreateOrganizationRequest request) {

        return ApiResponse.success(
            "Organization created successfully",
            organizationService.create(request));
    }
}
