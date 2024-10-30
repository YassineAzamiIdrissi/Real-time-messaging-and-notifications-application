package com.example.real_time.CustomExceptions;

public class PasswordsMisMatchException extends RuntimeException {
    public PasswordsMisMatchException(String message) {
        super(message);
    }
}
