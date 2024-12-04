package com.orlov.SpringBootSecurityRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @ModelAttribute("authUser")
    public User addAuthenticatedUserModel(Principal principal) {
        if (principal != null) {
            return userService.getUserByUsername(principal.getName());
        }
        return null;
    }
    
    @GetMapping
    public String userPage() {
        return "user/index";
    }
}
