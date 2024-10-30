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
public class RegistrationRequest {
    @NotEmpty(message = "firstname can't be empty")
    @NotNull(message = "firstname has to be specified")
    private String firstName;
    @NotEmpty(message = "lastName can't be empty")
    @NotNull(message = "lastName has to be specified")
    private String lastName;
    @NotEmpty(message = "email can't be empty")
    @NotNull(message = "email has to be specified")
    @Email(message = "Invalid email format !")
    private String email;
    @NotEmpty(message = "password can't be empty")
    @NotNull(message = "password has to be specified")
    @Size(min = 8, message = "password can't be less than 8 characters")
    private String password;
}
