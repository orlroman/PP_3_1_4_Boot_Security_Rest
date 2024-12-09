package com.orlov.SpringBootSecurityRest.service;

import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        return optionalUser.get();
    }
    
    @Override
    @Transactional
    public void save(User user) {
        Optional<User> userFromDB = userRepository.findByEmail(user.getEmail());
        
        if (userFromDB.isPresent()) {
            throw new EntityExistsException(String.format("User with this '%s' already exist", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void delete(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.delete(userOptional.get());
    }
    
    @Override
    @Transactional
    public void update(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        return userOptional.get();
    }
}
