package com.cleaning.service.controllers;


import com.cleaning.service.dto.LoginRequest;
import com.cleaning.service.dto.UserRegisterRequest;
import com.cleaning.service.dto.UserResponse;
import com.cleaning.service.entities.User;
import com.cleaning.service.services.CustomUserDetailsService;
import com.cleaning.service.services.JwtUtil;
import com.cleaning.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );
        } catch (DisabledException e) {
            throw new Exception("User is disabled", e);
        } catch (LockedException e) {
            throw new Exception("User account is locked", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.getUsername());
System.out.println("userDetails: " + userDetails);
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserResponse response = userService.registerUser(userRegisterRequest);
        return ResponseEntity.ok(response);
    }
}
