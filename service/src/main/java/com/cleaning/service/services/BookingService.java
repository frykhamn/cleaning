package com.cleaning.service.services;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.dto.CreateBookingRequest;
import com.cleaning.service.entities.Booking;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking save();
    List<BookingDTO> findAll();
    Optional<Booking> findById(Long id);
    BookingDTO createBooking(CreateBookingRequest createBookingRequest, UserDetails userDetails);
}
