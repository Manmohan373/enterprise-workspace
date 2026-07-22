package com.enterprise.auth.service;

import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.request.LogoutRequest;
import com.enterprise.auth.dto.request.RefreshTokenRequest;
import com.enterprise.auth.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

}
