package com.cleaning.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
