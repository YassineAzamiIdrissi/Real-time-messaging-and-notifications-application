package com.example.real_time.Notification;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification, Integer userId) {
        messagingTemplate.convertAndSendToUser(
                String.valueOf(userId),
                "/notifications",
                notification
        );
    }
}
