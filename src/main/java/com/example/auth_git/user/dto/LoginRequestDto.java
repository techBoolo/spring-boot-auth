package com.example.auth_git.user.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
