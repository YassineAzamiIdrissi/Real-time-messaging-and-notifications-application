package com.example.real_time.User;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.FriendRequest.FriendRequestRespDto;
import com.example.real_time.Group.Group;
import com.example.real_time.Group.GroupMemberRespDto;
import com.example.real_time.GroupMembership.GroupMemberShipRepository;
import com.example.real_time.GroupMembership.GroupMembership;
import com.example.real_time.GroupMessage.GroupMessage;
import com.example.real_time.GroupMessage.GroupMessageDto;
import com.example.real_time.Message.Message;
import com.example.real_time.Message.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final FriendRequestRepository reqRepo;
    private final UserRepository userRepo;
    private final GroupMemberShipRepository membershipRepo;

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

    public UserRespDto requestToFriendDto(FriendRequest request, User connected,
                                          Group group) {
        User friend;
        if (Objects.equals(request.getSender().getId(), connected.getId())) {
            friend = request.getReceiver();
        } else {
            friend = request.getSender();
        }
        if (group != null) {
            var joined = membershipRepo.isUserAlreadyJoined(
                    friend.getId(), group.getId()
            );
            return UserRespDto.builder().
                    id(friend.getId()).
                    firstname(friend.getFirstName()).
                    lastname(friend.getLastName()).
                    isFriend(true).
                    joined(friend.getCreatedAt()).
                    belongsToGroup(joined).
                    build();
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

    public GroupMemberRespDto membershipToGroupMemberRespDto(
            GroupMembership membership
    ) {
        return GroupMemberRespDto.builder().
                id(membership.getMember().getId()).
                joinedAt(membership.getCreatedAt()).
                member(membership.getMember().fullName()).
                status(membership.getStatus()).
                isAdmin(Objects.equals(membership.getMember().getId(),
                        membership.getGroup().getGrpCreator().getId())).
                build();
    }

    public GroupMessageDto GroupMessageToGroupMessageDto(
            GroupMessage grpMessage
    ) {
        return GroupMessageDto.builder().
                groupId(grpMessage.getGroup().getId()).
                senderId(grpMessage.getSender().getId()).
                content(grpMessage.getContent()).
                sentAt(grpMessage.getCreatedDate()).
                senderName(grpMessage.getSender().fullName()).
                build();
    }
}
