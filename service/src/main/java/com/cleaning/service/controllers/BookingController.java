package com.cleaning.service.controllers;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.dto.BookingResponse;
import com.cleaning.service.dto.CreateBookingRequest;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.repositories.UserRepository;
import com.cleaning.service.services.BookingServiceImpl;
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
    private BookingServiceImpl bookingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody CreateBookingRequest createBookingRequest, @AuthenticationPrincipal UserDetails userDetails) {

        BookingDTO createdBooking = bookingService.createBooking(createBookingRequest, userDetails);

        BookingResponse response = new BookingResponse();
        response.setMessage("Booking created successfully");
        response.setStatus("success");
        response.setBooking(createdBooking);

        return ResponseEntity.ok(response);
    }

 /*   @GetMapping
    public List<Booking> getBookingsByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        // Hämta bokningar för inloggad användare
        return bookingService.getBookingsForUser(2324L);
    }*/

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getBookings() {
        List<BookingDTO> bookingList = bookingService.findAll();
        return ResponseEntity.ok(bookingList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        BookingDTO bookingDTO = bookingService.getBookingById(id);

        BookingResponse response = new BookingResponse();
        response.setMessage("Booking retrieved successfully");
        response.setStatus("success");
        response.setBooking(bookingDTO);

        return ResponseEntity.ok(response);
    }
}
