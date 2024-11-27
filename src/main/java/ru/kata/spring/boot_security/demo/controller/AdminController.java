package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
    @GetMapping(value = "/")
    public String admin() {
        return "admin/index";
    }
    
//    @GetMapping(value = "/new")
//    public String newPerson(@ModelAttribute("user") User User) {
//        return "users/new";
//    }
//
//    @PostMapping(value = "/save")
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "users/new";
//        }
//        userService.save(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping(value = "/edit")
//    public String edit(@RequestParam("id") int id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "users/edit";
//    }
//
//    @PostMapping(value = "/edit")
//    public String update(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult,
//                         @RequestParam("id") int id) {
//
//        if (bindingResult.hasErrors()) {
//            return "users/edit";
//        }
//        userService.update(id, user);
//        return "redirect:/users";
//    }
//
//    @PostMapping(value = "/delete")
//    public String delete(@RequestParam("id") int id) {
//        userService.delete(id);
//        return "redirect:/users";
//    }
}
