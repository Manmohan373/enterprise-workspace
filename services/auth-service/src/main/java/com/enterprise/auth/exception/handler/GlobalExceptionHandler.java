package com.enterprise.auth.exception.handler;

import com.enterprise.auth.dto.common.ErrorResponse;
import com.enterprise.auth.exception.ApiException;
import com.enterprise.auth.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(
        ResourceNotFoundException ex,
        HttpServletRequest request) {

        return ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
        ApiException ex,
        HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(ex.getStatus().value())
            .error(ex.getStatus().getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(ex.getStatus())
            .body(response);
    }

}
