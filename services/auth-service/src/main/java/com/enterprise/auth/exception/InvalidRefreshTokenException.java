package com.enterprise.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends ApiException{

    public  InvalidRefreshTokenException(HttpStatus status, String message) {
        super(status, message);
    }
}
