package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.OrganizationUpdateRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import com.enterprise.user.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrganizationService {


    OrganizationResponse createOrganization(CreateOrganizationRequest request);

    OrganizationResponse getOrganization(UUID id);


    PageResponse<OrganizationResponse> getOrganizations(Pageable pageable);

    OrganizationResponse updateOrganization(
        UUID id,
        OrganizationUpdateRequest request
    );

    void deleteOrganization(UUID id);

    SearchResponse<OrganizationResponse> search(SearchRequest request);

}
