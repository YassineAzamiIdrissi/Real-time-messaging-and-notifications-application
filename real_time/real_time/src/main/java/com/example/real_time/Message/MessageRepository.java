package com.example.real_time.Message;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends
        JpaRepository<Message, Integer> {

    @Query("""
                       SELECT message From Message message 
                       WHERE (message.sender.id = :connectedId 
                       AND message.receiver.id = :userId) OR 
                       (message.sender.id = :userId 
                       AND message.receiver.id = :connectedId) 
            """)
    Page<Message> loadConversationBetweenUsers
            (Pageable pageable, Integer connectedId, Integer userId);


    @Query("""
                        SELECT message FROM Message message 
                        WHERE 
                        ((message.sender.id = :connectedId AND 
                        message.receiver.id = :userId) OR 
                        (message.sender.id = :userId AND 
                        message.receiver.id = :connectedId)
                        ) ORDER BY message.id DESC
            """)
    List<Message> getLastMessageOfDiscussion(Integer userId, Integer connectedId);

    @Modifying
    @Query("""
                        DELETE FROM Message message 
                        WHERE 
                        ((message.receiver.id = :senderId AND 
                        message.sender.id = :receiverId) OR 
                        (message.receiver.id = :receiverId AND 
                        message.sender.id = :senderId))
            """)
    void deleteAllBySenderIdAndReceiverId
            (Integer senderId, Integer receiverId);
}
