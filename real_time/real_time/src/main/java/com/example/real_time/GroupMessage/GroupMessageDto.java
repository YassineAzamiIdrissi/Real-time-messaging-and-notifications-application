package com.example.real_time.GroupMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupMessageDto {
    private Integer groupId;
    private Integer senderId;
    private String content;
    private LocalDateTime sentAt;
    private String senderName;
}
