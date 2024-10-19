package com.cleaning.service.entities;

import com.cleaning.service.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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


    // Bidirektionell @OneToMany relation till Booking
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> customerBookings;

    @OneToMany(mappedBy = "cleaner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> cleanerBookings;

    /**
     * PreResist skapar publicId innan användaren sparas i databasen
     */
    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID().toString();
    }
}