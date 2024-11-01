package com.example.real_time.User;

import com.example.real_time.CustomExceptions.AppUserNotFoundException;
import com.example.real_time.CustomExceptions.FriendRequestNotFoundException;
import com.example.real_time.CustomExceptions.InvalidOperationException;
import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.FriendRequest.FriendRequestRepository;
import com.example.real_time.FriendRequest.FriendRequestRespDto;
import com.example.real_time.Pagination.PageResponse;
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
        return reqRepo.save(request).getId();
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
        return reqRepo.save(concernedRequest).getId();
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
        concernedReq.setUpdatedAt(LocalDateTime.now()   );
        return reqRepo.save(concernedReq).getId();
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
}
