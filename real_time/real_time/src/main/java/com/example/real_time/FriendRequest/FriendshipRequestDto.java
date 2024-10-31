package com.example.real_time.FriendRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FriendshipRequestDto {
    @NotNull(message = "A request must be sent from some user..")
    @Min(value = 1, message = "An id can't be less than or equal 0 !")
    private Integer senderId;
    @NotNull(message = "A request must be sent to some user..")
    @Min(value = 1, message = "An id can't be less than or equal 0 !")
    private Integer receiverId;
}
