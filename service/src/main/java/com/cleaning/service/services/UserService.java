package com.cleaning.service.services;

import com.cleaning.service.dto.UserRegisterRequest;
import com.cleaning.service.dto.UserResponse;
import com.cleaning.service.entities.User;
import com.cleaning.service.enums.UserRole;
import com.cleaning.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());

        // Assign default role to new user
        user.setUserRole(UserRole.CUSTOMER);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setStatus(savedUser != null ? "success" : "failed");
        response.setMessage(savedUser.getUsername() + "registered successfully");

        return response;
    }

    public User registerCleaner(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign cleaner role to new user
        user.setUserRole(UserRole.CLEANER);

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
