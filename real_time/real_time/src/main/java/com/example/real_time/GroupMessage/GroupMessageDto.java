package com.example.real_time.GroupMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupMessageDto {
    private Integer groupId;
    private Integer senderId;
    private String content;
    private LocalDateTime sentAt;
}
