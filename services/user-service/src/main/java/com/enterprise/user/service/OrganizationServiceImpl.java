package com.enterprise.user.service;

import com.enterprise.user.dto.request.CreateOrganizationRequest;
import com.enterprise.user.dto.request.UpdateOrganizationRequest;
import com.enterprise.user.dto.response.OrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{
    @Override
    public OrganizationResponse create(CreateOrganizationRequest request) {
        return null;
    }

    @Override
    public OrganizationResponse getById(UUID id) {
        return null;
    }

    @Override
    public List<OrganizationResponse> getAll() {
        return List.of();
    }

    @Override
    public OrganizationResponse update(UUID id, UpdateOrganizationRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
