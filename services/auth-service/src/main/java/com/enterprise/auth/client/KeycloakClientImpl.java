package com.enterprise.auth.client;

import com.enterprise.auth.config.KeycloakProperties;
import com.enterprise.auth.dto.internal.KeycloakTokenResponse;
import com.enterprise.auth.dto.request.LoginRequest;
import com.enterprise.auth.dto.request.LogoutRequest;
import com.enterprise.auth.dto.request.RefreshTokenRequest;
import com.enterprise.auth.dto.response.LoginResponse;
import com.enterprise.auth.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {

    private final RestClient restClient;
    private final KeycloakProperties properties;

    @Override
    public LoginResponse login(LoginRequest request) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", request.username());
        formData.add("password", request.password());

        return sendPostRequest(formData,"token");
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", request.refreshToken());

        return sendPostRequest(formData,"token");
    }

    @Override
    public void logout(LogoutRequest request) {

        MultiValueMap<String, String> formData =
            new LinkedMultiValueMap<>();

        formData.add("refresh_token", request.refreshToken());
        sendPostRequest(formData, "logout");
    }

    private LoginResponse sendPostRequest(MultiValueMap<String, String> formData, String endPoint){
        String tokenUrl = String.format(
            "%s/realms/%s/protocol/openid-connect/%s",
            properties.serverUrl(),
            properties.realm(),
            endPoint
        );
        System.out.println(tokenUrl);

        formData.add("client_id", properties.clientId());
        formData.add("client_secret", properties.clientSecret());
        try{
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
        }catch (HttpClientErrorException.BadRequest ex) {
            throw new InvalidRefreshTokenException(
                HttpStatus.UNAUTHORIZED,"Refresh token is invalid or expired.");
        }
    }
}
