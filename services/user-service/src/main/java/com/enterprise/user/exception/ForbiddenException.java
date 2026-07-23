package com.enterprise.user.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException{

    public  ForbiddenException(HttpStatus status, String message) {
        super(status, message);
    }
}
