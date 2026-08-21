package com.CodeCraftHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors.
     *
     * Example:
     *
     * {
     *     "name": "Name is required",
     *     "status": "Status must be exactly one of..."
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Handles invalid JSON or invalid date formats.
     *
     * For example:
     *
     * "target_date": "tomorrow"
     *
     * instead of:
     *
     * "target_date": "2026-09-15"
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
public ResponseEntity<Map<String, String>> handleInvalidJson(
        HttpMessageNotReadableException exception) {

    exception.printStackTrace();

    Map<String, String> error = new HashMap<>();

    error.put(
            "error",
            "Invalid JSON or field format"
    );

    error.put(
            "details",
            exception.getMostSpecificCause().getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
}

    /**
     * Handles requests for courses that don't exist.
     */
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCourseNotFound(
            CourseNotFoundException exception) {

        Map<String, String> error = new HashMap<>();

        error.put("error", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    /**
     * Handles courses.json read/write errors.
     */
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<Map<String, String>> handleFileStorageError(
            FileStorageException exception) {

        Map<String, String> error = new HashMap<>();

        error.put(
                "error",
                "Unable to read or write courses.json."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    /**
     * Catch unexpected errors.
     *
     * This prevents implementation details
     * from being exposed to the API client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericError(
            Exception exception) {

        Map<String, String> error = new HashMap<>();

        error.put(
                "error",
                "An unexpected error occurred."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
