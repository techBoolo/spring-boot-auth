package com.example.auth_git.user;

import com.example.auth_git.user.dto.LoginRequestDto;
import com.example.auth_git.user.dto.LoginResponseDto;
import com.example.auth_git.user.dto.UserRequestDto;
import com.example.auth_git.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public List<UserResponseDto> getUsers(Principal principal, HttpSession session){
        System.out.println(principal.getName() + " and " + session.getId());
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request,
            HttpServletResponse response){
        return userService.login(loginRequestDto, request, response);
    }
}
