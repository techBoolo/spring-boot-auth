package com.example.auth_git.user.dto;

public record LoginResponseDto(
        String email,
        String accessToken,
        String message
) { }
