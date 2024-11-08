package com.example.real_time.FriendRequest;

import com.example.real_time.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    // Mistake : there is no = NULL in JPQL but only IS Null...
    @Query("""
                    SELECT (COUNT(*)>0) AS isRequestAlreadySent 
                    FROM FriendRequest request 
                    WHERE request.sender.id = :userId 
                    AND request.receiver.id = :friendId
                    AND request.updatedAt IS null 
            """)
    boolean isRequestAlreadySent(Integer userId, Integer friendId);

    @Query("""
                        SELECT (COUNT(*)>0) AS isAlreadyFriends 
                        FROM FriendRequest request 
                        WHERE ((request.sender.id = :userId 
                        AND request.receiver.id = :friendId) OR 
                        (request.sender.id = :friendId 
                        AND request.receiver.id = :userId)) 
                        AND request.accepted = true 
            """)
    boolean isAlreadyFriends(Integer userId, Integer friendId);

    @Query("""
                 SELECT request FROM FriendRequest request 
                 WHERE (request.sender.id = :userId 
                 OR request.receiver.id = :userId)  
                 AND request.accepted = true    
            """)
    Page<FriendRequest> getAllThisUserFriends(Pageable pageable, Integer userId);


    @Query("""
                    SELECT request FROM FriendRequest request
                    WHERE request.receiver.id = :userId 
            """)
    Page<FriendRequest> getUserFriendRequests
            (Pageable pageable, Integer userId);


    @Query("""
                        SELECT (COUNT(*)>0) 
                        FROM FriendRequest request 
                        WHERE request.sender.id = :connectedId 
                        AND request.receiver.id = :userId      
                        AND request.updatedAt IS null            
            """)
    boolean isUserAddableByConnected(Integer connectedId, Integer
            userId);

    @Query("""
                      SELECT request FROM FriendRequest request
                      WHERE ((
                        request.sender.id = :connectedId 
                        AND request.receiver.id = :friendId 
                      ) OR (
                      request.sender.id = :friendId 
                        AND request.receiver.id = :connectedId 
                      )) AND 
                      request.accepted = true  
            """)
    FriendRequest findFriendShip(Integer connectedId, Integer friendId);

    @Query("""
                        SELECT req FROM FriendRequest req 
                        WHERE (req.sender.id = :connectedId OR 
                        req.receiver.id = :connectedId) AND  
                        req.accepted = true 
                        AND NOT EXISTS 
                        (
                            SELECT membership FROM GroupMembership membership
                            WHERE membership.group.id = :grpId
                        )
            """)
    Page<FriendRequest> findNotJoinedFriendsToGroup(Pageable pageable,
                                                    Integer grpId, Integer connectedId);
}

