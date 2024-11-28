package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    
    User getUserByEmail(String username);
    boolean save(User user);
    void delete(long id);
    void update(long id, User user);
    List<User> getUsers();
    User getUserById(long id);
}
