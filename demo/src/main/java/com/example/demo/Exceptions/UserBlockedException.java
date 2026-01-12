package com.example.demo.Exceptions;


public class UserBlockedException extends RuntimeException {

    public UserBlockedException(String message) {
        super(message);
    }
     public UserBlockedException(String message,Throwable cause) {
        super(message);
    }
}
