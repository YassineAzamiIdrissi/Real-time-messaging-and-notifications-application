package com.example.real_time.FriendRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query("""
                    SELECT (COUNT(*)>0) AS isRequestAlreadySent 
                    FROM FriendRequest request 
                    WHERE request.sender.id = :userId 
                    AND request.receiver.id = :friendId
                    AND request.updatedAt = null 
            """)
    boolean isRequestAlreadySent(Integer userId, Integer friendId);
}
