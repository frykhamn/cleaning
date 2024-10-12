package com.cleaning.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
}
