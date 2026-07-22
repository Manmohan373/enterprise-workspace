package com.enterprise.auth.client;

import com.enterprise.auth.config.KeycloakProperties;
import com.enterprise.auth.dto.internal.KeycloakTokenResponse;
import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {

    private final RestClient restClient;
    private final KeycloakProperties properties;

    @Override
    public LoginResponse login(LoginRequest request) {
        String tokenUrl = String.format(
            "%s/realms/%s/protocol/openid-connect/token",
            properties.serverUrl(),
            properties.realm()
        );
        System.out.println(properties.serverUrl());
        System.out.println(properties.realm());

        System.out.println(tokenUrl);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "password");
        formData.add("client_id", properties.clientId());
        formData.add("client_secret", properties.clientSecret());
        formData.add("username", request.username());
        formData.add("password", request.password());

        KeycloakTokenResponse response = restClient.post()
            .uri(tokenUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(formData)
            .retrieve()
            .body(KeycloakTokenResponse.class);

        return LoginResponse.builder()
            .accessToken(response.accessToken())
            .refreshToken(response.refreshToken())
            .expiresIn(response.expiresIn())
            .tokenType(response.tokenType())
            .build();
    }

}
