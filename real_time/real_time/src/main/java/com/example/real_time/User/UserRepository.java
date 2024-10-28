package com.example.real_time.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
