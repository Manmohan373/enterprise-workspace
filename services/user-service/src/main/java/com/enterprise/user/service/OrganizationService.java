package com.enterprise.user.service;

import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.UpdateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {


    OrganizationResponse createOrganization(CreateOrganizationRequest request);

    OrganizationResponse getOrganization(UUID id);


    List<OrganizationResponse> getAll();

    OrganizationResponse update(
        UUID id,
        UpdateOrganizationRequest request);

    void delete(UUID id);

}
