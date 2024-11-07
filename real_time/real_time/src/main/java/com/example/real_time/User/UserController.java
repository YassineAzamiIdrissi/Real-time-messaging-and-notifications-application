package com.example.real_time.User;

import com.example.real_time.FriendRequest.FriendRequestRespDto;
import com.example.real_time.Group.GroupMemberRespDto;
import com.example.real_time.Group.GroupRespDto;
import com.example.real_time.Message.MessageDto;
import com.example.real_time.Pagination.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    @GetMapping
    ResponseEntity<PageResponse<UserRespDto>> getAllTimeLineUsers(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.getDisplayableUsers(
                        page, size, authentication
                ));
    }

    @PostMapping("add/{userId}")
    ResponseEntity<Integer> addUser(
            @PathVariable("userId") int userId,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(
                        userService.addFriend(userId, authentication)
                );
    }

    @GetMapping("friends")
    ResponseEntity<PageResponse<UserRespDto>>
    fetchAllThisUserFriends(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication auth
    ) {
        return ResponseEntity.ok().
                body(userService.fetchAllUserFriends(
                        page, size, auth
                ));
    }

    @PatchMapping("accept-req/{req-id}")
    ResponseEntity<Integer> acceptRequest(
            @PathVariable(name = "req-id") int reqId,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.acceptFriendRequest(reqId, authentication));
    }

    @GetMapping("reqs")
    ResponseEntity<PageResponse<FriendRequestRespDto>>
    getAllReceivedRequests(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.getUserFriendRequests(page, size, authentication));
    }

    @PatchMapping("refuse-req/{req-id}")
    ResponseEntity<Integer> refuseRequest(
            @PathVariable("req-id") Integer reqId,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.refuseFriendRequest(reqId, authentication)
                );
    }

    @DeleteMapping("unfriend/{userId}")
    ResponseEntity<?> unfriendUser(
            @PathVariable("userId") Integer userId,
            Authentication authentication
    ) {
        System.out.println("Controller Executed....");
        userService.unfriendUser(userId, authentication);
        return ResponseEntity.ok().
                build();
    }

    @PostMapping("messages/{userId}")
    ResponseEntity<Integer> sendMessage(
            @PathVariable("userId") Integer userId,
            @RequestBody MessageDto messageDto,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.sendMessage
                        (userId, messageDto, authentication)
                );
    }

    @GetMapping("conversations/{user-id}")
    ResponseEntity<PageResponse<MessageDto>>
    loadConversation(@PathVariable("user-id") Integer userId,
                     @RequestParam(name = "page", defaultValue = "0", required = false)
                     int page,
                     @RequestParam(name = "size", defaultValue = "5",
                             required = false) int size,
                     Authentication authentication) {
        return ResponseEntity.ok().
                body(userService.
                        loadConversation(userId, page, size, authentication));
    }

    @GetMapping("{user-id}")
    ResponseEntity<UserRespDto> getSpecificUser(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok().
                body(userService.getSpecificUser(userId));
    }

    @GetMapping("last-message/{user-id}")
    ResponseEntity<MessageDto> getLastMessage(
            @PathVariable("user-id") Integer userId,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.getLastMessageOfConversation(
                        userId,
                        authentication
                ));
    }

    @DeleteMapping("conversations/{user-id}")
    public ResponseEntity<?> deleteConversation(
            @PathVariable("user-id") Integer userId,
            Authentication authentication
    ) {
        userService.deleteConversation(userId, authentication);
        return ResponseEntity.ok().
                build();
    }

    @PostMapping("groups")
    ResponseEntity<Integer> createGroup(@RequestParam(name = "groupName") String groupName,
                                        Authentication authentication) {
        return ResponseEntity.ok().
                body(userService.createGroup(
                        groupName, authentication
                ));
    }

    @GetMapping("groups/creator")
    ResponseEntity<PageResponse<GroupRespDto>>
    realAllConnectedUserGroups(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(
                        userService.readAllConnectedUserGroups(
                                page,
                                size,
                                authentication
                        )
                );
    }

    @GetMapping("group/members/{groupId}")
    ResponseEntity<PageResponse<GroupMemberRespDto>> getGroupMembers(
            @PathVariable("groupId") Integer groupId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        return ResponseEntity.ok().
                body(userService.getGroupMembers(
                        page
                        , size,
                        groupId
                ));
    }

    @GetMapping("groups/{group-id}")
    ResponseEntity<GroupRespDto> getSpecGroup(
            @PathVariable("group-id") Integer groupId
    ) {
        return ResponseEntity.ok().
                body(userService.getSpecGroup(groupId));
    }

    @PostMapping("group/members/{groupId}")
    ResponseEntity<Integer> addGroupMember(
            @PathVariable("groupId") int groupId,
            @RequestParam(name = "friendId") int friendId,
            Authentication authentication
    ) {
        return ResponseEntity.ok().
                body(userService.addFriendToCreatedGroup
                        (friendId, groupId, authentication));
    }

}

