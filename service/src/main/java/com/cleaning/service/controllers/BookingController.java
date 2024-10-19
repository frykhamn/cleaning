package com.cleaning.service.controllers;

import com.cleaning.service.entities.Booking;
import com.cleaning.service.entities.User;
import com.cleaning.service.repositories.UserRepository;
import com.cleaning.service.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the logged-in user
        User user = userRepository.findByUsername(userDetails.getUsername());
        // Set the logged-in user as the customer of the booking
        booking.setCustomer(user);
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getBookings(@AuthenticationPrincipal UserDetails userDetails) {
        // Hämta bokningar för inloggad användare
        return bookingService.getBookingsForUser(2324L);
    }
}
