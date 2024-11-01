package com.example.real_time.FriendRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FriendRequestRespDto {
    private Integer id;
    private String sender;
    private LocalDateTime sentAt;
    private boolean accepted;
    private boolean answered;
}
