package com.ewms.common.response;

import java.time.Instant;
import java.util.List;

/**
 * Standard API response wrapper used across the application.
 *
 * @param success  indicates whether the request was successful
 * @param message  human-readable message
 * @param data     response payload
 * @param errors   validation or business errors
 * @param timestamp response creation time
 * @param <T>      response payload type
 */
public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    List<String> errors,
    Instant timestamp
) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
            true,
            message,
            data,
            List.of(),
            Instant.now()
        );
    }

    public static <T> ApiResponse<T> failure(String message, List<String> errors) {
        return new ApiResponse<>(
            false,
            message,
            null,
            errors == null ? List.of() : errors,
            Instant.now()
        );
    }
}
