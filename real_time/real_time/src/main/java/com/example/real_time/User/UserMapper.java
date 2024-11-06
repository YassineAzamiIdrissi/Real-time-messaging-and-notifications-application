package com.example.real_time.User;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.FriendRequest.FriendRequestRespDto;
import com.example.real_time.Message.Message;
import com.example.real_time.Message.MessageDto;
import com.example.real_time.Message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final FriendRequestRepository reqRepo;
    private final MessageRepository msgRepo;
    private final UserRepository userRepo;

    public UserRespDto userToUserRespDto
            (User user, User connected) {
        if (connected == null) {
            return UserRespDto.builder().
                    id(user.getId()).
                    firstname(user.getFirstName()).
                    lastname(user.getLastName()).
                    joined(user.getCreatedAt()).
                    build();
        }
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

    public Message msgReqToMsg(MessageDto msgReq) {
        User sender = userRepo.findById(msgReq.getSenderId()).
                orElseThrow(() -> new AppUserNotFoundException(
                        "User with id " + msgReq.getSenderId() + " isn't found"
                ));
        User receiver = userRepo.findById(msgReq.getReceiverId()).
                orElseThrow(() -> new AppUserNotFoundException(
                        "User with id " + msgReq.getReceiverId() + " isn't found"));
        return Message.builder().
                sender(sender).
                receiver(receiver).
                content(msgReq.getContent()).
                build();
    }

    public MessageDto msgToMessageDto(Message msg) {
        return MessageDto.
                builder().
                senderId(msg.getSender().getId()).
                receiverId(msg.getReceiver().getId()).
                content(msg.getContent()).
                sentAt(msg.getCreatedAt()).
                build();
    }
}
