package com.example.auth_git.utils;

import com.example.auth_git.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service("securityService")
public class SecurityService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwner(Long id, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId().equals(id))
                .orElse(false);
    }
}
