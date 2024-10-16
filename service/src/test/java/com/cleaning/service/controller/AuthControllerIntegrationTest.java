package com.cleaning.service.controller;

import com.cleaning.service.entities.User;
import com.cleaning.service.enums.UserRole;
import com.cleaning.service.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setEmail("testuser@test.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhoneNumber("1234567890");
        user.setUserRole(UserRole.CUSTOMER);
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        User newUser = userRepository.findByUsername("newuser");
        if (newUser != null) {
            userRepository.delete(newUser);
        }

        User testUser = userRepository.findByUsername("test");
        if (testUser != null) {
            userRepository.delete(testUser);
        }

    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"test\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newuser\", \"password\":\"password123\", \"email\":\"newuser@test.com\", \"firstName\":\"New\", \"lastName\":\"User\", \"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isCreated());
    }
}