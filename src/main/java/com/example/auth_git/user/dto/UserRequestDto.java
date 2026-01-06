package com.example.auth_git.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotBlank
        String name,

        @Email @NotBlank
        String email,

        @NotBlank
        @Size(min = 3)
        String password,

        @NotBlank
        String passwordConfirmation) {

    public UserRequestDto {
        if (!password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
