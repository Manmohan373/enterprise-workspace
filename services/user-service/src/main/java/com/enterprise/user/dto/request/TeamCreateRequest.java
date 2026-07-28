package com.enterprise.user.dto.request;

import java.util.UUID;

public record TeamCreateRequest(

    UUID projectId,

    String name,

    String code,

    String description

) {}
