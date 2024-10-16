package com.cleaning.service.services;

import com.cleaning.service.dto.UserDto;
import com.cleaning.service.dto.UserRegisterRequest;
import com.cleaning.service.entities.User;
import com.cleaning.service.enums.UserRole;
import com.cleaning.service.exceptions.UserAlreadyExistsException;
import com.cleaning.service.exceptions.UserRegistrationException;
import com.cleaning.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserDto registerUser(UserRegisterRequest userRegisterRequest) {
        if (isUserExists(userRegisterRequest.getUsername())) {
            throw new UserAlreadyExistsException("User with name "
                    + userRegisterRequest.getUsername() + " already exists");
        }

        User user = buildUserFromRequest(userRegisterRequest);
        User savedUser = userRepository.save(user);

        if (savedUser == null) {
            throw new UserRegistrationException("Failed to register user");
        }
        return new UserDto(savedUser);
    }


    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }


    private User buildUserFromRequest(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setUserRole(UserRole.CUSTOMER);
        return user;
    }

    private boolean isUserExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

}
