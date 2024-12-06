package com.orlov.SpringBootSecurityRest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    @GetMapping
    public String userPage() {
        return "user/index";
    }
}
