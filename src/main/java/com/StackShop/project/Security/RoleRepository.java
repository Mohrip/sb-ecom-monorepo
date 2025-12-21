package com.StackShop.project.Security;

import com.StackShop.project.User.Role;
import com.StackShop.project.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(UserRole roleName);



}
