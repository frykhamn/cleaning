package com.cleaning.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String status;
    private String notes;

    private Long customerId;
    private Long cleanerId;
    private Long cleaningTypeId;
    private Long addressId;

}
