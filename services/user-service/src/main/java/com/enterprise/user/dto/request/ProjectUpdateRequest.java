package com.enterprise.user.dto.request;

import com.enterprise.user.entity.ProjectStatus;
import com.enterprise.user.entity.ProjectType;

import java.time.Instant;

public record ProjectUpdateRequest(

    String name,

    String clientName,

    String description,

    ProjectType projectType,

    ProjectStatus status,

    Instant startDate,

    Instant endDate

) {
}
