package com.example.auth_git.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private String statusCode;
    private boolean success;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>("000", true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>("000", true, message, data);
    }

    public static <T> ApiResponse<T> error(T data){
        return new ApiResponse<>("001", false, "error", data);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>("001", false, message, null);
    }
}
