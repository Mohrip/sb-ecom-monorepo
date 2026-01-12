package com.StackShop.project.security;

import com.StackShop.project.user.Role;
import com.StackShop.project.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(UserRole roleName);


}
