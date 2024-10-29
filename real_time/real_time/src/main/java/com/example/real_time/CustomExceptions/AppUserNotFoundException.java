package com.example.real_time.CustomExceptions;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
