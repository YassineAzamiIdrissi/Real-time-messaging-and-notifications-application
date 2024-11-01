package com.example.real_time.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT user FROM User user
                 WHERE user.id != :userId
                 AND user.enabled = TRUE
                 AND user.locked = FALSE
                 AND NOT EXISTS (
                     SELECT 1 FROM FriendRequest request
                     WHERE request.receiver.id = user.id
                     AND request.sender.id = :userId
                 )
            """)
    Page<User> findDisplayableUsersForConnectedOne
            (Integer userId, Pageable pageable);

}
