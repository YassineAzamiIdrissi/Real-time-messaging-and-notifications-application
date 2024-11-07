package com.example.real_time.Group;

import com.example.real_time.GroupMembership.GroupMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupMemberRespDto {
    private Integer id;
    private String member;
    private LocalDateTime joinedAt;
    private GroupMemberStatus status;
    private boolean isAdmin;
}
