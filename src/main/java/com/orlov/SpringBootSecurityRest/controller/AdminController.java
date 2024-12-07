package com.orlov.SpringBootSecurityRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.RoleService;
import com.orlov.SpringBootSecurityRest.service.UserService;
import com.orlov.SpringBootSecurityRest.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    
    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }
    
//    @ModelAttribute("authUser")
//    public User addAuthenticatedUserModel(Principal principal) {
//        if (principal != null) {
//            return userService.getUserByUsername(principal.getName());
//        }
//        return null;
//    }
    
    @GetMapping
    public String index() {
        return "admin/newIndex";
    }
    
    @GetMapping(value = "/new")
    public String newUser() {
        return "admin/new";
    }

//    @PostMapping(value = "/save")
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
//
//        model.addAttribute("roles", roleService.getRoles());
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "admin/new";
//        }
//
//        userService.save(user);
//        return "redirect:/admin";
//    }
    
//    @PostMapping(value = "/edit/{id}")
//    public String update(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") long id, Model model) {
//
//        model.addAttribute("users", userService.getUsers());
//        model.addAttribute("roles", roleService.getRoles());
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("editError", id);
//            return "admin/index";
//        }
//
//        user.setId(id);
//        userService.update(user);
//        return "redirect:/admin";
//    }
    
//    @PostMapping(value = "/delete/{id}")
//    public String delete(@PathVariable("id") long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
}
