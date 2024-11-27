package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
    
    @GetMapping(value = "/user")
    public String user() {
        return "user";
    }
}
