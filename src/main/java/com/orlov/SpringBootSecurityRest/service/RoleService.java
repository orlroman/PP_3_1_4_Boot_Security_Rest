package com.orlov.SpringBootSecurityRest.service;

import com.orlov.SpringBootSecurityRest.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRoleByTitle(String title);
    void save(Role role);
    
    
}
