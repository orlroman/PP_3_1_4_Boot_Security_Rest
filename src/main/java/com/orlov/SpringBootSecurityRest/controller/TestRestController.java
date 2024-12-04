package com.orlov.SpringBootSecurityRest.controller;

import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TestRestController {
    
    private final UserService userService;
    
    @Autowired
    public TestRestController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping(value = "/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }
}
