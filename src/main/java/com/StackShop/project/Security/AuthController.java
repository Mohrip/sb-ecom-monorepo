package com.StackShop.project.Security;

import com.StackShop.project.User.Role;
import com.StackShop.project.User.User;
import com.StackShop.project.User.UserRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        PasswordEncoder Encoder;

        @Autowired
        RoleRepository roleRepository;

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
            Authentication authentication;
            try {
                authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            } catch (AuthenticationException exception) {
                Map<String, Object> map = new HashMap<>();
                map.put("message", "Bad credentials");
                map.put("status", false);
                return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            //String jwtToken = jwtUtils.generateJwtTokenFromUsername(userDetails);
            //String jwtToken = jwtUtils.generateJwtTokenFromUsername(userDetails.getUsername());
            //1

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);




            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(), roles, jwtCookie.toString());

           // return ResponseEntity.ok(response);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(response);
        }


        @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
            if(userRepository.existsByUsername((signUpRequest.getUsername()))) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));

            }
            if(userRepository.existsByEmail((signUpRequest.getEmail()))) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create new user's account
            User user = new User(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    //signUpRequest.getPassword()
                    Encoder.encode(signUpRequest.getPassword())
            );

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByRoleName(UserRole.CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin" -> {
                            Role adminRole = roleRepository.findByRoleName(UserRole.ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        }
                        case "seller" -> {
                            Role modRole = roleRepository.findByRoleName(UserRole.SELLER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                        }
                        default -> {
                            Role userRole = roleRepository.findByRoleName(UserRole.CUSTOMER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                        }
                    }
                });
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}



