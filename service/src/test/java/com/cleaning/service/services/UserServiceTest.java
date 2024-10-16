package com.cleaning.service.services;

import com.cleaning.service.dto.UserDto;
import com.cleaning.service.dto.UserRegisterRequest;
import com.cleaning.service.entities.User;
import com.cleaning.service.enums.UserRole;
import com.cleaning.service.exceptions.UserAlreadyExistsException;
import com.cleaning.service.exceptions.UserRegistrationException;
import com.cleaning.service.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password123");
        request.setEmail("newuser@example.com");
        request.setFirstName("New");
        request.setLastName("User");
        request.setPhoneNumber("0701234567");

        when(userRepository.findByUsername("newuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");
        savedUser.setPassword("encodedPassword");
        savedUser.setEmail("newuser@example.com");
        savedUser.setFirstName("New");
        savedUser.setLastName("User");
        savedUser.setPhoneNumber("0701234567");
        savedUser.setUserRole(UserRole.CUSTOMER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDto result = userServiceImpl.registerUser(request);

        // Assert
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        assertEquals("newuser@example.com", result.getEmail());
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("0701234567", result.getPhoneNumber());

        verify(userRepository, times(1)).findByUsername("newuser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        // Arrange
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("existinguser");

        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(existingUser);

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userServiceImpl.registerUser(request);
        });

        assertEquals("User with name existinguser already exists", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("existinguser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_SaveReturnsNull() {
        // Arrange
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("newuser");
        request.setPassword("password123");

        when(userRepository.findByUsername("newuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(null);

        // Act & Assert
        UserRegistrationException exception = assertThrows(UserRegistrationException.class, () -> {
            userServiceImpl.registerUser(request);
        });

        assertEquals("Failed to register user", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("newuser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUsers() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDto> result = userServiceImpl.getUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }
}
