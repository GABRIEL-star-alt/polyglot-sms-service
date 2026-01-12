package com.example.demo.Exceptions; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ApiError> handleUserBlocked(UserBlockedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        "USER_BLOCKED",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        "INTERNAL_ERROR",
                        "Something went wrong"
                ));
    }
}
