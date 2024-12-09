package com.orlov.SpringBootSecurityRest.config;

import com.orlov.SpringBootSecurityRest.entity.Role;
import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.RoleService;
import com.orlov.SpringBootSecurityRest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Component
public class DataInitialization {
    
    private final UserService userService;
    private final RoleService roleService;
    
    @Autowired
    public DataInitialization(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @PostConstruct
    public void init() {
        
        Role adminRole;
        try {
            adminRole = roleService.getRoleByTitle("ROLE_ADMIN");
        } catch (EntityNotFoundException e) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.save(adminRole);
        }
        
        Role userRole;
        try {
            userRole = roleService.getRoleByTitle("ROLE_USER");
        } catch (EntityNotFoundException e) {
            userRole = new Role("ROLE_USER");
            roleService.save(userRole);
        }
        

        User admin;
        try {
            userService.getUserByUsername("admin@example.com");
        } catch (EntityNotFoundException e) {
            admin = new User("Admin", "Admin", 30,
                    "admin@example.com", "admin", Set.of(adminRole, userRole));
            userService.save(admin);
        }
        
        User user;
        try {
            userService.getUserByUsername("user@example.com");
        } catch (EntityNotFoundException e) {
            user = new User("User", "User", 35,
                    "user@example.com", "user", Set.of(userRole));
            userService.save(user);
        }

    }
}
