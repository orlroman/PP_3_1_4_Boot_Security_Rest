package com.orlov.SpringBootSecurityRest.service;

import com.orlov.SpringBootSecurityRest.entity.User;

import java.util.List;

public interface UserService {
    
    User getUserByUsername(String username);
    void save(User user);
    void update(User user);
    void delete(long id);
    List<User> getUsers();
    User getUserById(long id);
}
