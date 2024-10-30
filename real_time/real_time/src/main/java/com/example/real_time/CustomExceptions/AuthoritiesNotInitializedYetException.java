package com.example.real_time.CustomExceptions;

public class AuthoritiesNotInitializedYetException extends RuntimeException {
    public AuthoritiesNotInitializedYetException(String message) {
        super(message);
    }
}
