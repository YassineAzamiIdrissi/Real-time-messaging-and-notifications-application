package com.example.real_time.GroupMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Integer> {

    @Query("""
                SELECT msg FROM GroupMessage msg 
                WHERE msg.group.id = :groupId
            """)
    Page<GroupMessage> getAllGroupMessages(Pageable pageable, int groupId);
}
