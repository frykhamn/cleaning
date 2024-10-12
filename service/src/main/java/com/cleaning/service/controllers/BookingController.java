package com.cleaning.service.controllers;

import com.cleaning.service.entities.Booking;
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

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking, @AuthenticationPrincipal UserDetails userDetails) {
        // Sätt inloggad användare som kund
        // Implementera metoden för att hämta användarobjektet från UserDetails
        //booking.setCustomer(/* hämta användare från UserDetails */);
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getBookings(@AuthenticationPrincipal UserDetails userDetails) {
        // Hämta bokningar för inloggad användare
       // Long userId = /* hämta användar-ID från UserDetails */;
        return bookingService.getBookingsForUser(2324L);
    }
}
