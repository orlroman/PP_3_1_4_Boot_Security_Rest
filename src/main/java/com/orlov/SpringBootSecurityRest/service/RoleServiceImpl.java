package com.orlov.SpringBootSecurityRest.service;

import com.orlov.SpringBootSecurityRest.entity.Role;
import com.orlov.SpringBootSecurityRest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        Optional<Role> optionalRole =  roleRepository.findByTitle(title);
        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException(String.format("Role '%s' not found", title));
        }
        return optionalRole.get();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException(String.format("Role with id: '%d' not found", id));
        }
        return optionalRole.get();
    }
    
    
    @Override
    @Transactional
    public void save(Role role) {
        Optional<Role> roleFromDB = roleRepository.findByTitle(role.getTitle());
        if (roleFromDB.isPresent()) {
            throw new EntityExistsException(String.format("Role '%s' is already exist", role.getTitle()));
        }
        roleRepository.save(role);
    }
}
