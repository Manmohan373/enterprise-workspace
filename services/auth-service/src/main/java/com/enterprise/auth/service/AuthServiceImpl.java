package com.enterprise.auth.service;

import com.enterprise.auth.client.KeycloakClient;
import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.request.LogoutRequest;
import com.enterprise.auth.dto.request.RefreshTokenRequest;
import com.enterprise.auth.dto.response.CurrentUserResponse;
import com.enterprise.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final KeycloakClient keycloakClient;

    @Override
    public LoginResponse login(LoginRequest request) {
        return keycloakClient.login(request);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        return keycloakClient.refreshToken(request);
    }

    @Override
    public void logout(LogoutRequest request) {
        keycloakClient.logout(request);
    }

    @Override
    public CurrentUserResponse me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        return CurrentUserResponse.builder()
            .id(jwt.getSubject())
            .username(jwt.getClaimAsString("preferred_username"))
            .email(jwt.getClaimAsString("email"))
            .firstName(jwt.getClaimAsString("given_name"))
            .lastName(jwt.getClaimAsString("family_name"))
            .build();
    }
}
