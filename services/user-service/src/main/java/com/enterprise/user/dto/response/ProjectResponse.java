package com.enterprise.user.dto.response;

import com.enterprise.user.entity.ProjectStatus;
import com.enterprise.user.entity.ProjectType;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(

    UUID id,

    UUID organizationId,

    String organizationName,

    String name,

    String code,

    String clientName,

    String description,

    ProjectType projectType,

    ProjectStatus status,

    Instant startDate,

    Instant endDate,

    Instant createdAt,

    Instant updatedAt

) {
}
