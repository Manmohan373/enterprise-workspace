package com.enterprise.user.controller;

import com.enterprise.user.dto.common.ApiResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ApiResponse<OrganizationResponse> create(
        @Valid @RequestBody CreateOrganizationRequest request) {

        return new ApiResponse<>(true,"Organization created successfully",organizationService.create(request));
    }
}
