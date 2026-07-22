package com.enterprise.auth.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(

    String accessToken,
    String refreshToken,
    Long expiresIn,
    String tokenType

) {
}
