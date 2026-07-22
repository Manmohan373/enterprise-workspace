package com.enterprise.auth.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException{

    public  ForbiddenException(HttpStatus status, String message) {
        super(status, message);
    }
}
