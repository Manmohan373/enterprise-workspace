package com.enterprise.user.dto.response;

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

    String address,

    Boolean active,

    Instant createdAt,

    Instant updatedAt

) {
}
