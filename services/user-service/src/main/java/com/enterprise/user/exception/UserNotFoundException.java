package com.enterprise.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException{

    public  UserNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
