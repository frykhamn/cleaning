package com.cleaning.service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateBookingRequest {

    @NotNull(message = "Datum och tid måste anges")
    @Future(message = "Datum och tid måste vara i framtiden")
    private LocalDateTime dateTime;

    @Size(max = 500, message = "Anteckningar får inte överstiga 500 tecken")
    private String notes;

    @NotNull(message = "Typ av städning måste anges")
    private Long cleaningTypeId;

    @NotNull(message = "Städare måste anges")
    private Long cleanerId;

    @NotNull(message = "Adress måste anges")
    private Long addressId;

    private Long customerId;
}