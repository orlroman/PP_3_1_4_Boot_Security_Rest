package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return user;
    }
    
    @Override
    @Transactional
    public boolean save(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());
        
        if (userFromDB != null) {
            return false;
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    
    @Override
    @Transactional
    public void delete(long id) {
    
    }
    
    @Override
    @Transactional
    public void update(long id, User user) {
    
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return null;
    }
}
