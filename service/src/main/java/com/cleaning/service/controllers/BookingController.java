package com.cleaning.service.controllers;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.dto.BookingResponse;
import com.cleaning.service.dto.CreateBookingRequest;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.entities.User;
import com.cleaning.service.repositories.UserRepository;
import com.cleaning.service.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookingDTO> createBooking(@RequestBody CreateBookingRequest createBookingRequest, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the logged-in user
        User user = userRepository.findByUsername(userDetails.getUsername());
        // Set the logged-in user as the customer of the booking
        booking.setCustomer(user);
        BookingDTO createdBooking = bookingService.createBooking(createdBooking);

        BookingResponse response = new BookingResponse();
        response.setMessage("Booking created successfully");
        response.setStatus("success");
        response.setBooking(booking);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Booking> getBookings(@AuthenticationPrincipal UserDetails userDetails) {
        // Hämta bokningar för inloggad användare
        return bookingService.getBookingsForUser(2324L);
    }
}
