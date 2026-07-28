package com.enterprise.user.dto.request;

import com.enterprise.user.entity.TeamStatus;

public record TeamUpdateRequest(

    String name,

    String description,

    TeamStatus status

) {}
