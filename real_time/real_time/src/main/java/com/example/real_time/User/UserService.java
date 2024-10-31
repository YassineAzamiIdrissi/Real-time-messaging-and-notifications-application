package com.example.real_time.User;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.CustomExceptions.InvalidOperationException;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.Pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final FriendRequestRepository reqRepo;
    private final UserMapper userMapper;

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
                stream().map(userMapper::userToUserRespDto).
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
                connectedUser.getId(), concernedUser.getId()
        );
        return 0;
    }
}
