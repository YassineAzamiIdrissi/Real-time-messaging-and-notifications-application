package com.example.real_time.Authority;

import java.util.Optional;

public interface AuthorityRepository {
    Optional<Authority> findByAuthority(String authority);
}
