package com.orlov.SpringBootSecurityRest.controller;

import com.orlov.SpringBootSecurityRest.entity.Role;
import com.orlov.SpringBootSecurityRest.entity.User;
import com.orlov.SpringBootSecurityRest.service.RoleService;
import com.orlov.SpringBootSecurityRest.service.UserService;
import com.orlov.SpringBootSecurityRest.util.UserErrorResponse;
import com.orlov.SpringBootSecurityRest.util.UserNotCreateException;
import com.orlov.SpringBootSecurityRest.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RestsController {
    
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    
    @Autowired
    public RestsController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }
    
    @GetMapping(value = "/user/auth")
    public User getAuthUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return userService.getUserByUsername(userDetails.getUsername());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
    }
    
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }
    
    @GetMapping(value = "/roles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }
    
    @PostMapping(value = "/new")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user, BindingResult bindingResult) {
        validate(user, bindingResult);
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    @PostMapping(value = "/edit/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        user.setId(id);
        validate(user, bindingResult);
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    private void validate(User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreateException(errorMessage.toString());
        }
    }
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UsernameNotFoundException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(EntityNotFoundException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(EntityExistsException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotCreateException exception) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(exception.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
