package com.example.real_time.GroupMessage;

import com.example.real_time.Message.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMessageService {
    private final SimpMessagingTemplate messagingTemplate;

    public void publishMessage(Integer groupId, GroupMessageDto message) {
        messagingTemplate.convertAndSendToUser(
                String.valueOf(groupId),
                "/grpChat",
                message
        );
    }
}
