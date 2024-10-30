package com.example.real_time.CustomExceptions;

public class IncorrectActivationCode extends RuntimeException {
    public IncorrectActivationCode(String message) {
        super(message);
    }
}
