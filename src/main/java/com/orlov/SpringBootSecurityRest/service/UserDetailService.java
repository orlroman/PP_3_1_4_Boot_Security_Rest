package com.orlov.SpringBootSecurityRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.repository.UserRepository;
import com.orlov.SpringBootSecurityRest.security.UserDetail;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return new UserDetail(user.get());
    }
}
