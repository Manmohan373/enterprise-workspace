package com.enterprise.user.exception.handler;

import com.enterprise.user.dto.common.ErrorResponse;
import com.enterprise.user.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
        ResourceNotFoundException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request.getRequestURI()
        );
    }

    @ExceptionHandler({
        ConflictException.class,
        DuplicateResourceException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(
        RuntimeException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidSearchFieldException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSearchField(
        InvalidSearchFieldException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            request.getRequestURI()
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
        ApiException ex,
        HttpServletRequest request) {

        return buildResponse(
            ex.getStatus(),
            ex.getMessage(),
            request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
        Exception ex,
        HttpServletRequest request) {

        log.error("Unhandled exception", ex);

        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Something went wrong. Please try again later.",
            request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
        HttpStatus status,
        String message,
        String path) {

        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .path(path)
            .build();

        return ResponseEntity
            .status(status)
            .body(response);
    }
}
