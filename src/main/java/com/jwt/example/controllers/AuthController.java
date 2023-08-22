package com.jwt.example.controllers;

import com.jwt.example.dto.ResponseDto.JwtResponse;
import com.jwt.example.dto.requestDto.JwtRequest;
import com.jwt.example.dto.requestDto.UserRequest;
import com.jwt.example.entity.User;
import com.jwt.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //localhost:8080/auth/login
    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest request) {
        return userService.login(request);
    }

    //localhost:8080/auth/save
    @PostMapping("/save")
    public User createUser(@Valid @RequestBody UserRequest userRequest) throws Exception {
        return userService.create(userRequest);
    }
}
