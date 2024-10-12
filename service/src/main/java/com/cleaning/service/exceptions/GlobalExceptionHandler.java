package com.cleaning.service.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(Exception ex) {
        return new ResponseEntity<>("Felaktigt användarnamn eller lösenord", HttpStatus.UNAUTHORIZED);
    }

    // Hantera fler exception-typer vid behov
}