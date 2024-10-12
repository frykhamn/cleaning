package com.cleaning.service.services;

import com.cleaning.service.entities.Booking;
import com.cleaning.service.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByCustomerId(userId);
    }
}