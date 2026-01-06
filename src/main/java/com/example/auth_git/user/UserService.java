package com.example.auth_git.user;

import com.example.auth_git.exception.NotFoundException;
import com.example.auth_git.exception.RecordExistsException;
import com.example.auth_git.user.dto.UserRequestDto;
import com.example.auth_git.user.dto.UserResponseDto;
import com.example.auth_git.user.entity.User;
import com.example.auth_git.user.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
}
