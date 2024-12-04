package com.orlov.SpringBootSecurityRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.orlov.SpringBootSecurityRest.entity.Role;
import com.orlov.SpringBootSecurityRest.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Role getRoleByTitle(String title) {
        Role role =  roleRepository.findByTitle(title);
        if (role == null) {
            throw new EntityNotFoundException(String.format("Role '%s' not found", title));
        }
        return role;
    }
    
    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
}
