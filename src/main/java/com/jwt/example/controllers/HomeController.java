package com.jwt.example.controllers;

import com.jwt.example.entity.User;
import com.jwt.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    //localhost:8080/home/user
    @GetMapping("/user")
    public List<User> getUser() {
        System.out.println("getting Users");
        return userService.getUsers();
    }

    //localhost:8080/home/current-user
    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal){
        return principal.getName().toUpperCase();
    }
}
