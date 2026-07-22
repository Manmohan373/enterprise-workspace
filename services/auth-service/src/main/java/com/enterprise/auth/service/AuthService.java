package com.enterprise.auth.service;

import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}
