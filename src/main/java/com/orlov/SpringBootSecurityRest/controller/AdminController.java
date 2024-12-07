package com.orlov.SpringBootSecurityRest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
    @GetMapping
    public String index() {
        return "admin/index";
    }
    
    @GetMapping(value = "/new")
    public String newUser() {
        return "admin/new";
    }
    
}
