package com.enterprise.user.exception;


public class InvalidSearchFieldException extends RuntimeException {

    public InvalidSearchFieldException(String field) {
        super("Invalid search field: " + field);
    }

}
