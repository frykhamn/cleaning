package com.cleaning.service.dto;

import com.cleaning.service.entities.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookingRequest {
    
    private String dateTime;
    private String notes;
    private Long customerId;
    private Long cleanerId;
    private Long cleaningTypeId;
    private Address address;
}
