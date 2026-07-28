package com.enterprise.user.service;

import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.dto.request.ProjectCreateRequest;
import com.enterprise.user.dto.request.ProjectUpdateRequest;
import com.enterprise.user.dto.response.ProjectResponse;

import java.util.UUID;

public interface ProjectService {

    ProjectResponse create(ProjectCreateRequest request);

    ProjectResponse get(UUID id);

    ProjectResponse update(UUID id,
                           ProjectUpdateRequest request);

    void delete(UUID id);

    SearchResponse<ProjectResponse> search(SearchRequest request);

}
