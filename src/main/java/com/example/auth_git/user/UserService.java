package com.example.auth_git.user;

import com.example.auth_git.exception.NotFoundException;
import com.example.auth_git.exception.RecordExistsException;
import com.example.auth_git.user.dto.LoginRequestDto;
import com.example.auth_git.user.dto.LoginResponseDto;
import com.example.auth_git.user.dto.UserRequestDto;
import com.example.auth_git.user.dto.UserResponseDto;
import com.example.auth_git.user.entity.User;
import com.example.auth_git.user.mapper.UserMapper;
import com.example.auth_git.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public UserResponseDto register(UserRequestDto requestDto){

        if(userRepository.existsByEmail(requestDto.email())){
            throw new RecordExistsException("record already exists.");
        }

        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserResponseDto> getUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    public UserResponseDto getUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("record not found"));

        return userMapper.toDto(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(userDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponseDto(userDetails.getUsername(), accessToken,"login successfully.");
    }

    public UserResponseDto getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordExistsException("Record not found."));
        return userMapper.toDto(user);
    }
}
