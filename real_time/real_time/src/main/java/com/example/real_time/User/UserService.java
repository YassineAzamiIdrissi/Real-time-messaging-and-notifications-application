package com.example.real_time.User;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.CustomExceptions.FriendRequestNotFoundException;
import com.example.real_time.CustomExceptions.InvalidOperationException;
import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.FriendRequest.FriendRequestRespDto;
import com.example.real_time.Group.*;
import com.example.real_time.GroupMembership.GroupMemberShipRepository;
import com.example.real_time.GroupMembership.GroupMembership;
import com.example.real_time.GroupMessage.GroupMessage;
import com.example.real_time.GroupMessage.GroupMessageDto;
import com.example.real_time.GroupMessage.GroupMessageRepository;
import com.example.real_time.GroupMessage.GroupMessageService;
import com.example.real_time.Message.Message;
import com.example.real_time.Message.MessageDto;
import com.example.real_time.Message.MessageRepository;
import com.example.real_time.Message.MessageService;
import com.example.real_time.Notification.Notification;
import com.example.real_time.Notification.NotificationService;
import com.example.real_time.Pagination.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.real_time.GroupMembership.GroupMemberStatus.ADMIN;
import static com.example.real_time.GroupMembership.GroupMemberStatus.MEMBER;
import static com.example.real_time.Notification.NotificationType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final FriendRequestRepository reqRepo;
    private final MessageRepository msgRepo;
    private final GroupRepository groupRepo;
    private final GroupMemberShipRepository memberShipRepo;
    private final UserMapper userMapper;
    private final GroupMapper groupMapper;
    private final GroupMessageService groupMessageService;
    private final NotificationService notificationService;
    private final MessageService msgService;
    private final GroupMessageRepository groupMessageRepo;

    public PageResponse<UserRespDto>
    getDisplayableUsers(int page, int size, Authentication authentication) {
        User connectedUser = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").
                        descending()
        );
        Page<User> dispUsers = userRepo.findDisplayableUsersForConnectedOne(
                connectedUser.getId(),
                pageable
        );
        List<UserRespDto> content = dispUsers.
                stream().map(user -> userMapper.userToUserRespDto(
                        user, connectedUser
                )).
                toList();
        return new PageResponse<>(
                dispUsers.isFirst(),
                dispUsers.isLast(),
                content,
                dispUsers.getTotalElements(),
                dispUsers.getTotalPages(),
                dispUsers.getNumber(),
                dispUsers.getSize()
        );
    }

    public Integer addFriend(Integer userId, Authentication authentication) {
        User connectedUser = (User) authentication.getPrincipal();
        if (Objects.equals(connectedUser.getId(), userId)) {
            throw new InvalidOperationException
                    ("You can't add yourself as a friend !!");
        }
        User concernedUser = userRepo.findById(userId).
                orElseThrow(() -> new AppUserNotFoundException
                        ("User with id " + userId + " isn't found"));

        boolean isRequestAlreadySent = reqRepo.isRequestAlreadySent(
                connectedUser.getId(), userId
        );
        System.out.println("LOOOL " + isRequestAlreadySent);
        boolean isAlreadyFriends = reqRepo.isAlreadyFriends(
                connectedUser.getId(), concernedUser.getId()
        );
        if (isRequestAlreadySent) {
            throw new InvalidOperationException("Request has been already sent to this user.. ");
        }
        if (isAlreadyFriends) {
            throw new InvalidOperationException("You are already friends...");
        }
        FriendRequest request = FriendRequest.builder().
                sender(connectedUser).
                receiver(concernedUser).
                accepted(false).
                build();
        var saved = reqRepo.save(request);
        Notification notification = Notification.builder().
                title("New request").
                content(connectedUser.fullName() + " sent a friend request").
                type(FRIEND_REQUEST).
                build();
        notificationService.sendNotification(notification,
                concernedUser.getId());
        return saved.getId();
    }

    public PageResponse<UserRespDto>
    fetchAllUserFriends(int page, int size, Authentication auth) {
        User connectedUser = (User) auth.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").
                        descending()
        );
        Page<FriendRequest> userFriends =
                reqRepo.getAllThisUserFriends(pageable, connectedUser.getId());
        List<UserRespDto> content =
                userFriends.stream().
                        map((req) -> userMapper.requestToFriendDto(
                                req, connectedUser
                        )).
                        toList();
        return new PageResponse<>(
                userFriends.isFirst(),
                userFriends.isLast(),
                content,
                userFriends.getTotalElements(),
                userFriends.getTotalPages(),
                userFriends.getNumber(),
                userFriends.getSize()
        );
    }


    public Integer acceptFriendRequest(Integer reqId,
                                       Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        FriendRequest concernedRequest = reqRepo.findById(reqId).
                orElseThrow(
                        () -> new FriendRequestNotFoundException
                                ("Friend request identified by " + reqId + " isn't found !")
                );
        if (!Objects.equals(concernedRequest.getReceiver().getId(),
                connected.getId())) {
            throw new InvalidOperationException
                    ("you can't accept requests that aren't sent to you !");
        }
        concernedRequest.setAccepted(true);
        var saved = reqRepo.save(concernedRequest);
        Notification notification = Notification.builder().
                title("Request accepted").
                content(connected.fullName() + " accepted your request").
                type(FRIEND_REQUEST_ACCEPTED).
                build();
        notificationService.sendNotification
                (notification, concernedRequest.getSender().getId());
        return saved.getId();
    }

    public Integer refuseFriendRequest(Integer reqId,
                                       Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        FriendRequest concernedReq = reqRepo.findById(reqId).
                orElseThrow(() -> new FriendRequestNotFoundException(
                        "Friend request identified by " + reqId + " isn't found !"
                ));
        if (!Objects.equals(concernedReq.getReceiver().getId(), connected.getId())) {
            throw new InvalidOperationException("you can't refuse requests that aren't sent to you !");
        }
        concernedReq.setAccepted(false);
        concernedReq.setUpdatedAt(LocalDateTime.now());
        var saved = reqRepo.save(concernedReq);
        Notification notification = Notification.builder().
                title("Request refused").
                content(connected.fullName() + " refused your request..").
                type(FRIEND_REQUEST_REFUSED).
                build();
        notificationService.sendNotification(
                notification, concernedReq.getSender().getId()
        );
        return saved.getId();
    }

    public PageResponse<FriendRequestRespDto>
    getUserFriendRequests(
            int page,
            int size,
            Authentication authentication
    ) {
        User connectedUser = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by("createdAt").
                        descending());
        Page<FriendRequest> requests =
                reqRepo.getUserFriendRequests(
                        pageable, connectedUser.getId()
                );
        List<FriendRequestRespDto>
                content = requests.stream().map(
                userMapper::friendReqToFriendReqRespDto
        ).toList();
        return new PageResponse<>(
                requests.isFirst(),
                requests.isLast(),
                content,
                requests.getTotalElements(),
                requests.getTotalPages(),
                requests.getNumber(),
                requests.getSize()
        );
    }

    public void unfriendUser(Integer userid, Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        User concernedFriend = userRepo.findById(userid).
                orElseThrow(
                        () -> new AppUserNotFoundException
                                ("user with id " + userid + " is not found !")
                );
        boolean isAlreadyFriend = reqRepo.isAlreadyFriends(
                connected.getId(), concernedFriend.getId()
        );
        if (!isAlreadyFriend) {
            throw new InvalidOperationException
                    ("you can't unfriend this user... " +
                            "since it isn't already your friend");
        }
        FriendRequest concernedRequest =
                reqRepo.findFriendShip(
                        connected.getId(),
                        concernedFriend.getId()
                );
        reqRepo.deleteById(concernedRequest.getId());
    }

    public Integer sendMessage(Integer toId,
                               MessageDto message,
                               Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        User receiver = userRepo.findById(toId).orElseThrow
                (() -> new AppUserNotFoundException(
                        "User with id" + toId + " isn't found"
                ));
        message.setSenderId(connected.getId());
        message.setReceiverId(toId);
        Message savedMessage = userMapper.msgReqToMsg(message);
        Message saved = msgRepo.save(savedMessage);
        msgService.sendMessage(message.getReceiverId(), message);
        return saved.getId();
    }


    public PageResponse<MessageDto> loadConversation(
            Integer userId,
            int page,
            int size,
            Authentication authentication) {
        User concernedUser = userRepo.findById(userId).
                orElseThrow(() -> new AppUserNotFoundException
                        ("User with id " + userId + " is not found !"));
        User connectedUser = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").ascending()
        );
        Page<Message> conversation =
                msgRepo.loadConversationBetweenUsers(
                        pageable,
                        connectedUser.getId(),
                        concernedUser.getId()
                );
        List<MessageDto> content = conversation.stream().map(
                userMapper::msgToMessageDto
        ).toList();
        return new PageResponse<>(
                conversation.isFirst(),
                conversation.isLast(),
                content,
                conversation.getTotalElements(),
                conversation.getTotalPages(),
                conversation.getNumber(),
                conversation.getSize()
        );
    }

    public UserRespDto getSpecificUser(Integer userId) {
        return userRepo.findById(userId).
                map((user) -> userMapper.userToUserRespDto(user, null)).
                orElseThrow(
                        () -> new AppUserNotFoundException
                                ("User with id : " + userId + " isn't found")
                );
    }

    public MessageDto getLastMessageOfConversation(Integer userId,
                                                   Authentication authentication) {
        User concernedUser = userRepo.findById(userId).
                orElseThrow(() -> new AppUserNotFoundException
                        ("User with id " + userId + " not found"));
        User connectedUser = (User) authentication.getPrincipal();
        List<Message> messages = msgRepo.getLastMessageOfDiscussion(
                connectedUser.getId(),
                concernedUser.getId()
        );
        MessageDto dto = new MessageDto();
        String content;
        if (messages.isEmpty()) {
            content = "";
            dto.setSenderId(0);
            dto.setReceiverId(0);
        } else {
            content = messages.get(0).getContent();
            dto.setSenderId(messages.get(0).getSender().getId());
            dto.setReceiverId(messages.get(0).getReceiver().getId());
            dto.setSentAt(messages.get(0).getCreatedAt());
        }
        dto.setContent(content);
        return dto;
    }

    public void deleteConversation(
            Integer userId,
            Authentication authentication
    ) {
        User concernedUser = userRepo.findById(
                userId).orElseThrow(
                () -> new
                        AppUserNotFoundException
                        ("User with id " + userId + " isn't found")
        );
        User connectedUser = (User) authentication.getPrincipal();
        msgRepo.deleteAllBySenderIdAndReceiverId(
                connectedUser.getId(),
                concernedUser.getId()
        );
    }

    public Integer createGroup(String groupName, Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        Group group = Group.builder().name(groupName).
                grpCreator(connected).
                build();
        boolean isGroupAlreadyExists = groupRepo.isGroupAlreadyExists(
                connected.getId(), groupName
        );
        if (groupName == null || groupName.isEmpty()) {
            throw new InvalidOperationException("Group name has to be specified !");
        }
        if (isGroupAlreadyExists) {
            throw new InvalidOperationException("Group already exists !");
        }
        var savedGroup = groupRepo.save(group);
        GroupMembership membership = GroupMembership.builder().
                group(savedGroup).
                member(connected).
                status(ADMIN).
                build();
        memberShipRepo.save(membership);
        return savedGroup.getId();
    }

    public PageResponse<GroupRespDto> readAllConnectedUserGroups(
            int page,
            int size,
            Authentication authentication
    ) {
        User connected = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );
        Page<Group> concernedGroups =
                groupRepo.findGroupsOfConnectedUser(
                        pageable, connected.getId()
                );
        List<GroupRespDto> content =
                concernedGroups.stream().map(
                        groupMapper::groupToResp
                ).toList();
        return new PageResponse<>(
                concernedGroups.isFirst(),
                concernedGroups.isLast(),
                content,
                concernedGroups.getTotalElements(),
                concernedGroups.getTotalPages(),
                concernedGroups.getNumber(),
                concernedGroups.getSize()
        );
    }

    public PageResponse<GroupMemberRespDto> getGroupMembers(
            int page,
            int size,
            int groupId
    ) {
        Group concernedGroup = groupRepo.findById(groupId).
                orElseThrow(
                        () -> new InvalidOperationException
                                ("Group with id " + groupId + " isn't found !")
                );
        Pageable pageable = PageRequest.of(page, size, Sort.by
                ("createdAt").descending());
        Page<GroupMembership> memberships =
                memberShipRepo.findGroupMemberships(
                        pageable,
                        groupId
                );
        List<GroupMemberRespDto> members =
                memberships.stream().map(
                        userMapper::membershipToGroupMemberRespDto
                ).toList();
        return new PageResponse<>(
                memberships.isFirst(),
                memberships.isLast(),
                members,
                memberships.getTotalElements(),
                memberships.getTotalPages(),
                memberships.getNumber(),
                memberships.getSize()
        );
    }

    public GroupRespDto getSpecGroup(Integer groupId) {
        return groupRepo.findById(groupId).map(groupMapper::groupToResp).
                orElseThrow(() -> new InvalidOperationException(
                        "Group with id " + groupId + " isn't found"
                ));
    }

    public Integer addFriendToCreatedGroup(Integer friendId,
                                           Integer grpId,
                                           Authentication authentication) {
        User concernedUser = userRepo.findById(friendId).
                orElseThrow(() -> new AppUserNotFoundException(
                        "User with id " + friendId + " isn't found !"
                ));
        User connectedUser = (User) authentication.getPrincipal();
        boolean isAlreadyFriend = reqRepo.isAlreadyFriends(
                concernedUser.getId(), connectedUser.getId()
        );
        if (!isAlreadyFriend) {
            throw new InvalidOperationException
                    ("This user isn't in your friends list !");
        }
        Group concernedGroup = groupRepo.findById(grpId).
                orElseThrow(() -> new InvalidOperationException
                        ("Group with id " + friendId + " isn't found !"));
        if (!Objects.equals(concernedGroup.getGrpCreator().getId(),
                connectedUser.getId())) {
            throw new InvalidOperationException
                    ("You can't modify groups you didn't create !");
        }
        boolean isAlreadyJoined = memberShipRepo.isUserAlreadyJoined(
                concernedUser.getId(), concernedGroup.getId()
        );
        if (isAlreadyJoined) {
            throw new InvalidOperationException
                    ("This user belong already to this group");
        }
        GroupMembership membership = GroupMembership.builder().
                group(concernedGroup).
                member(concernedUser).
                status(MEMBER).
                build();
        var saved = memberShipRepo.save(membership);
        Notification notif = Notification.builder().
                type(ADDED_TO_GOURP).
                content(concernedUser.fullName() + " added you to group " + concernedGroup.getName()).
                build();

        notificationService.sendNotification(notif, friendId);
        return saved.getId();
    }

    public Integer sendGroupMessage(GroupMessageDto grpMessage,
                                    Authentication authentication) {
        User connected = (User) authentication.getPrincipal();
        Group concernedGroup = groupRepo.findById(grpMessage.getGroupId()).orElseThrow(
                () -> new InvalidOperationException
                        ("Group with id " + grpMessage.getGroupId() + " isn't found")
        );
        GroupMessage groupMessage = GroupMessage.builder().
                group(concernedGroup).
                sender(connected).
                content(grpMessage.getContent()).
                build();
        var saved = groupMessageRepo.save(groupMessage);
        groupMessageService.publishMessage(concernedGroup.getId(), grpMessage);
        return saved.getId();
    }

    public PageResponse<GroupMessageDto> loadGroupChatMessages(
            int page,
            int size,
            int groupId,
            Authentication authentication
    ) {
        Group concernedGroup = groupRepo.findById(groupId).
                orElseThrow(() -> new InvalidOperationException
                        ("Group with id " + groupId + " isn't found"));
        User connected = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").ascending()
        );
        Page<GroupMessage> messages =
                groupMessageRepo.getAllGroupMessages(
                        pageable, groupId
                );
        List<GroupMessageDto> content =
                messages.stream().
                        map(userMapper::GroupMessageToGroupMessageDto).
                        toList();
        return new PageResponse<>(
                messages.isFirst(),
                messages.isLast(),
                content,
                messages.getTotalElements(),
                messages.getTotalPages(),
                messages.getNumber(),
                messages.getSize()

        );
    }

    public PageResponse<GroupRespDto> loadJoinedGroups(
            Authentication authentication,
            int page,
            int size
    ) {
        User connected = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").ascending()
        );
        Page<Group> groups = groupRepo.findJoinedGroupsByThisUser(
                pageable, connected.getId()
        );
        List<GroupRespDto> content = groups.stream().map(
                groupMapper::groupToResp
        ).toList();
        return new PageResponse<>(
                groups.isFirst(),
                groups.isLast(),
                content,
                groups.getTotalElements(),
                groups.getTotalPages(),
                groups.getNumber(),
                groups.getSize()
        );
    }


}

