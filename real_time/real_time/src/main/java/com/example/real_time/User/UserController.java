package com.example.real_time.User;

import com.example.real_time.Pagination.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
