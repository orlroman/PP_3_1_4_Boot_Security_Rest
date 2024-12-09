package com.orlov.SpringBootSecurityRest.controller;

import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping(value = "/auth")
    public User getAuthUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return userService.getUserByUsername(userDetails.getUsername());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
    }
    
}
