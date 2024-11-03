package com.example.real_time.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("""
                       SELECT message From Message message 
                       WHERE message.sender.id = :connectedId 
                       AND message.receiver.id = :userId 
            """)
    Page<Message> loadConversationBetweenUsers
            (Pageable pageable, Integer connectedId, Integer userId);
}
