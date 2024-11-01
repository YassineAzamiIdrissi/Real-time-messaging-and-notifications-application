package com.example.real_time.CustomExceptions;

public class FriendRequestNotFoundException extends RuntimeException {
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
