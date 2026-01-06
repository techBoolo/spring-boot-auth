package com.example.auth_git.user.dto;

public record UserResponseDto(
        Long id
        , String name
        , String email
        , String role
        , boolean enabled
) {
}
