package com.enterprise.user.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException{

    public  UnauthorizedException(HttpStatus status, String message) {
        super(status, message);
    }
}
