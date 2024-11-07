package com.example.real_time.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupRespDto {
    private LocalDateTime createdAt;
    private Integer id;
    private String name;
    private String creator;
    private int members;
}
