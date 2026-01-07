package com.example.auth_git.user;

import com.example.auth_git.user.dto.LoginRequestDto;
import com.example.auth_git.user.dto.LoginResponseDto;
import com.example.auth_git.user.dto.UserRequestDto;
import com.example.auth_git.user.dto.UserResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto userRequestDto){
        return userService.register(userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getUsers(@AuthenticationPrincipal User user){
        System.out.println(user.getUsername());
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id, authentication)")
    public UserResponseDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
    public UserResponseDto getUserByEmail(
            @PathVariable String email,
            Principal principal){
        System.out.println(principal.getName());
        return userService.getUserByEmail(email);
    }
}
