package com.example.real_time.Security;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepo.findByEmail(username).
                orElseThrow(() -> new AppUserNotFoundException("User with email " + username + " not found"));
    }
}
