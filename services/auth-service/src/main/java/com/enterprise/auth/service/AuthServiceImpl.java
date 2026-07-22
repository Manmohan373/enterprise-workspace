package com.enterprise.auth.service;

import com.enterprise.auth.client.KeycloakClient;
import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.request.RefreshTokenRequest;
import com.enterprise.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
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
}
