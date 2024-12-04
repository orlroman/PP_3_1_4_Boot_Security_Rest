package com.orlov.SpringBootSecurityRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.orlov.SpringBootSecurityRest.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByTitle(String title);
}
