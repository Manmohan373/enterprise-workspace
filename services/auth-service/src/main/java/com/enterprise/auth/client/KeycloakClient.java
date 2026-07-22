package com.enterprise.auth.client;

import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.response.LoginResponse;

public interface KeycloakClient {
    LoginResponse login(LoginRequest request);
}
