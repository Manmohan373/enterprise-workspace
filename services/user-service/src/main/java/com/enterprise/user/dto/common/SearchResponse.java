package com.enterprise.user.dto.common;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchResponse<T>(

    List<T> data,

    Integer page,

    Integer size,

    Long totalElements,

    int totalPages,

    Boolean first,

    Boolean last

) {
}
