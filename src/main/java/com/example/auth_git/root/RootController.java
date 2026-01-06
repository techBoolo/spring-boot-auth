package com.example.auth_git.root;

import com.example.auth_git.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RootController {

    @GetMapping
    public RootDto root() {
        return new RootDto("Server is running.");
    }

    @GetMapping("/health-check")
    public RootDto healthCheck(){
//        throw new NotFoundException("error");
        return new RootDto("Health Check, Ok!");
    }
}
