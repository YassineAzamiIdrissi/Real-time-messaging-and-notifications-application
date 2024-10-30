package com.example.real_time.CustomExceptions;

public class RecoveryCodeExpiredException extends RuntimeException {
    public RecoveryCodeExpiredException(String message) {
        super(message);
    }
}
