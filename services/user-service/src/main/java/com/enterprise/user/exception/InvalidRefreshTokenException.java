package com.enterprise.user.exception;

import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends ApiException{

    public  InvalidRefreshTokenException(HttpStatus status, String message) {
        super(status, message);
    }
}
