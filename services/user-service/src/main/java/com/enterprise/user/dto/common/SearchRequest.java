package com.enterprise.user.dto.common;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public record SearchRequest(

    Integer page,

    Integer size,

    String sortBy,

    String sortDirection,

    Map<String, Object> filters

) {

    public SearchRequest {

        page = page == null ? 0 : page;

        size = size == null ? 10 : size;

        sortBy = (sortBy == null || sortBy.isBlank())
            ? "createdAt"
            : sortBy;

        sortDirection = (sortDirection == null || sortDirection.isBlank())
            ? "DESC"
            : sortDirection;

        filters = filters == null
            ? new HashMap<>()
            : filters;
    }

}
