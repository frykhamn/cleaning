package com.cleaning.service.entities;

import com.cleaning.service.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String publicId;

    private String username;
    private String password;
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Enum userRole;

    /**
     * PreResist skapar publicId innan användaren sparas i databasen
     */
    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID().toString();
    }
}