package com.example.real_time.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRespDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDateTime joined;
    private boolean isFriend;
}
