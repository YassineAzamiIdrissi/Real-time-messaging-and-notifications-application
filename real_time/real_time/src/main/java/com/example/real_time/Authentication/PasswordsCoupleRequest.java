package com.example.real_time.Authentication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordsCoupleRequest {
    @NotNull(message = "new password has to be specified")
    @NotEmpty(message = "a password can't be empty !")
    @Size(min = 8, message = "a password can't be less than 8 characters")
    private String newPassword;
    private String confirmNewPassword;
}
