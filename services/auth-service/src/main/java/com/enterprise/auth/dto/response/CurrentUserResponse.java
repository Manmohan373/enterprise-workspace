package com.enterprise.auth.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record CurrentUserResponse(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    Set<String> roles
) {
}
