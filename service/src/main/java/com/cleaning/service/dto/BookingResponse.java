package com.cleaning.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingResponse {

        private String status;
        private String message;
        private BookingDTO booking;
}
