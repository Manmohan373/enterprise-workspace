package com.enterprise.user.dto.request;

import com.enterprise.user.entity.ProjectType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;
public record ProjectCreateRequest(

    @NotNull
    UUID organizationId,

    @NotBlank
    @Size(max = 150)
    String name,

    @NotBlank
    @Size(max = 50)
    String code,

    @Size(max = 150)
    String clientName,

    @Size(max = 1000)
    String description,

    @NotNull
    ProjectType projectType,

    Instant startDate,

    Instant endDate

) {}
