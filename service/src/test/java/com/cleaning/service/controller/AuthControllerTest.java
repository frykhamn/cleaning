package com.cleaning.service.controller;

import com.cleaning.service.controllers.AuthController;
import com.cleaning.service.dto.JwtResponse;
import com.cleaning.service.dto.LoginRequest;
import com.cleaning.service.dto.UserRegisterRequest;
import com.cleaning.service.security.CustomUserDetails;
import com.cleaning.service.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLoginSuccess() throws Exception {
        // Given: Mocka indata
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        // Mocka CustomUserDetails
        CustomUserDetails mockUserDetails = mock(CustomUserDetails.class);

        // Mocka Authentication-objektet
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        // Mocka authenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Mocka jwtUtil.generateToken()
        when(jwtUtil.generateToken(mockUserDetails)).thenReturn("mock-jwt-token");

        // When: Kör login-metoden
        ResponseEntity<?> response = authController.login(loginRequest);

        // Then: Kontrollera att JWT-token returnerades
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof JwtResponse);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("mock-jwt-token", jwtResponse.getToken());
    }

    @Test
    void testLoginFailureBadCredentials() throws Exception {
        // Given: Mocka indata
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");

        // Mocka ett undantag för felaktiga inloggningsuppgifter
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Incorrect username or password"));

        // When & Then: Kontrollera att rätt undantag kastas
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authController.login(loginRequest);
        });

        assertEquals("Incorrect username or password", exception.getMessage());
    }


    @Test
    void testRegistrationSuccess(){
        // Given: Mocka indata
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("testuser");
        userRegisterRequest.setPassword("password123");

        // Mocka CustomUserDetails
        CustomUserDetails mockUserDetails = mock(CustomUserDetails.class);

        // Mocka Authentication-objektet
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        // Mocka authenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Mocka jwtUtil.generateToken()
        when(jwtUtil.generateToken(mockUserDetails)).thenReturn("mock-jwt-token");

        // When: Kör login-metoden
        ResponseEntity<?> response = authController.login(new LoginRequest());

        // Then: Kontrollera att JWT-token returnerades
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof JwtResponse);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("mock-jwt-token", jwtResponse.getToken());
    }
}
