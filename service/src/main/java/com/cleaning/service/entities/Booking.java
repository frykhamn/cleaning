package com.cleaning.service.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "cleaner_id")
    private User cleaner;

    @ManyToOne
    @JoinColumn(name = "cleaning_type_id")
    private CleaningType cleaningType;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // Getter och setter
}
