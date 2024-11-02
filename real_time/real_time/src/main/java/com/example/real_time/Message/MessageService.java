package com.example.real_time.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(Integer userId, MessageDto message) {
        messagingTemplate.convertAndSendToUser(
                String.valueOf(userId),
                "/messages",
                message
        );
    }
}
