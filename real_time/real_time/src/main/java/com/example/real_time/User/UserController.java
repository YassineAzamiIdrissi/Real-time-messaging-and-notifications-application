package com.example.real_time.User;

import com.example.real_time.FriendRequest.FriendRequestRespDto;
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
}

