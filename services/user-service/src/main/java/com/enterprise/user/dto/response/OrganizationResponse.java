package com.enterprise.user.dto.response;

import com.enterprise.user.entity.OrganizationStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record OrganizationResponse(

    UUID id,

    String name,

    String code,

    String email,

    String phone,

    String website,

    String description,

    OrganizationStatus status,

    Instant createdAt,

    Instant updatedAt

) {
}
