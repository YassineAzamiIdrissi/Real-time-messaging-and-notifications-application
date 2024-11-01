package com.example.real_time.FriendShip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendshipRepository extends JpaRepository<FriendShips, Integer> {

    @Query("""
                    SELECT (COUNT(*)>0) FROM 
                    FriendShips friendship 
                    WHERE (friendship.user.id = :connectedId AND friendship.friend.id = :userId) 
                    OR (friendship.friend.id = :connectedId AND friendship.user.id = :userId)
                                        
            """)
    boolean isAlreadyFriends(Integer connectedId, Integer userId);
}
