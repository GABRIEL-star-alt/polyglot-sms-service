package com.example.demo.Exceptions;

/**
 * Standard error response format for APIs
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String code;
    private String message;

   
}
