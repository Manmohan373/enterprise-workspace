package com.enterprise.user.dto.response;

import com.enterprise.user.entity.TeamStatus;

import java.time.Instant;
import java.util.UUID;

public record TeamResponse(

    UUID id,

    UUID organizationId,

    String organizationName,

    String name,

    String code,

    String description,

    TeamStatus status,

    Instant createdAt,

    Instant updatedAt

) {}
