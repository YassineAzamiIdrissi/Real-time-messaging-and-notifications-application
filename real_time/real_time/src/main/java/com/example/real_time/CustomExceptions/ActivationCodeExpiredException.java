package com.example.real_time.CustomExceptions;

public class ActivationCodeExpiredException extends RuntimeException {
    public ActivationCodeExpiredException(String message) {
        super(message);
    }
}
