package com.StackShop.project.util;

import com.StackShop.project.security.RoleRepository;
import com.StackShop.project.user.Role;
import com.StackShop.project.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.findByRoleName(UserRole.ADMIN).isEmpty()) {
            roleRepository.save(new Role(UserRole.ADMIN));
            System.out.println("ADMIN role created");
        }

        if (roleRepository.findByRoleName(UserRole.CUSTOMER).isEmpty()) {
            roleRepository.save(new Role(UserRole.CUSTOMER));
            System.out.println("CUSTOMER role created");
        }

        if (roleRepository.findByRoleName(UserRole.SELLER).isEmpty()) {
            roleRepository.save(new Role(UserRole.SELLER));
            System.out.println("SELLER role created");
        }
    }
}
