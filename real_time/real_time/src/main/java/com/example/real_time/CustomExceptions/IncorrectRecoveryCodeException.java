package com.example.real_time.CustomExceptions;

public class IncorrectRecoveryCodeException extends RuntimeException {
    public IncorrectRecoveryCodeException(String message) {
        super(message);
    }
}
