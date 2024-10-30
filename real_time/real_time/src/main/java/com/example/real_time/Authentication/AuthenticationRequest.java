package com.example.real_time.Authentication;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationRequest {
    @NotEmpty(message = "email can't be empty")
    @NotNull(message = "email has to be specified")
    @Email(message = "Invalid email format !")
    private String email;
    @NotEmpty(message = "password can't be empty")
    @NotNull(message = "password has to be specified")
    @Size(min = 8, message = "password can't be less than 8 characters")
    private String password;
}
