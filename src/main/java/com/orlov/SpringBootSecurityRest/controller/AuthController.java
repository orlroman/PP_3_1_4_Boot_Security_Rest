package com.orlov.SpringBootSecurityRest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping
    public String index() {
        return "redirect:/user";
    }
    
    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
