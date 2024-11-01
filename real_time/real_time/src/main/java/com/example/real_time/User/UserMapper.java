package com.example.real_time.User;

import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.FriendRequest.FriendRequestRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final FriendRequestRepository reqRepo;

    public UserRespDto userToUserRespDto
            (User user, User connected) {
        return UserRespDto.builder().
                id(user.getId()).
                firstname(user.getFirstName()).
                lastname(user.getLastName()).
                joined(user.getCreatedAt()).
                isFriend(reqRepo.isAlreadyFriends(user.getId(), connected.getId())).
                build();
    }

    public UserRespDto requestToFriendDto(FriendRequest request, User connected) {
        User friend;
        if (Objects.equals(request.getSender().getId(), connected.getId())) {
            friend = request.getReceiver();
        } else {
            friend = request.getSender();
        }
        return UserRespDto.builder().
                id(friend.getId()).
                firstname(friend.getFirstName()).
                lastname(friend.getLastName()).
                isFriend(true).
                joined(friend.getCreatedAt()).
                build();
    }


    public FriendRequestRespDto friendReqToFriendReqRespDto(FriendRequest req) {
        return FriendRequestRespDto.builder().
                id(req.getId()).
                sender(req.getSender().fullName()).
                sentAt(req.getCreatedAt()).
                accepted(req.isAccepted()).
                answered(req.getUpdatedAt() != null).
                build();
    }
}
