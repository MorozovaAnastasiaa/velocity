package com.example.Velosity20.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotEmpty
        @Size(min = 1, max = 30, message = "The name is too short or too long")
        String name,

        @NotEmpty
        @Size(min = 1, max = 30, message = "The name is too short or too long")
        String username,

        @NotEmpty
        @Email
        @Size(max = 50, message = "The email is too long")
        String email,

        @NotEmpty
        @Size(min = 3, max = 50, message = "The password is too short or too long")
        String password
) {
}
