package com.example.real_time.User;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserRespDto userToUserRespDto(User user) {
        return UserRespDto.builder().
                firstname(user.getFirstName()).
                lastname(user.getLastName()).
                joined(user.getCreatedAt()).
                build();
    }
}
