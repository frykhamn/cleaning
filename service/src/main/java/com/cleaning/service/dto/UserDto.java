package com.cleaning.service.dto;

import com.cleaning.service.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String publicId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Enum userRole;

    public UserDto(User user) {
        this.publicId = user.getPublicId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.userRole = user.getUserRole();
    }

}

