package com.enterprise.auth.controller;

import com.enterprise.auth.dto.common.ApiResponse;
import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.request.LogoutRequest;
import com.enterprise.auth.dto.request.RefreshTokenRequest;
import com.enterprise.auth.dto.response.LoginResponse;
import com.enterprise.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest);
        return new ApiResponse<>(true,"Login Successful",response);
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refreshToken(
        @Valid @RequestBody RefreshTokenRequest request) {

        LoginResponse response = authService.refreshToken(request);

        return new ApiResponse<>(true,
            "Token refreshed successfully",
            response
        );
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
        @Valid @RequestBody LogoutRequest request) {

        authService.logout(request);

        return new ApiResponse<>(true,
            "Logout successful",
            null
        );
    }
}
