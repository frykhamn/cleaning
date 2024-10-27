package com.cleaning.service.services;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.dto.BookingResponse;
import com.cleaning.service.dto.CreateBookingRequest;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.entities.User;
import com.cleaning.service.mapper.BookingMapper;
import com.cleaning.service.repositories.BookingRepository;
import com.cleaning.service.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;


    @Autowired
    public BookingService(UserRepository userRepository,
                          BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    public BookingDTO createBooking(CreateBookingRequest createBookingRequest, UserDetails userDetails) {
        // Hämta den inloggade användarens ID
        Long userId = getLoggedInUserId(userDetails);

        // Map CreateBookingRequest to Booking entity
        Booking booking = bookingMapper.toEntity(createBookingRequest);

        // Set the customer ID in the Booking entity
        booking.setCustomer(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId)));

        // Spara bokningen i databasen
        Booking savedBooking = bookingRepository.save(booking);

        // Mappa den sparade Booking entiteten till BookingDTO
        return savedBooking != null ? bookingMapper.toDTO(savedBooking) : null;
    }

/*    public List<BookingDTO> getCustomerBookings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return user.getCustomerBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getCleanerBookings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return user.getCleanerBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }*/

    private Long getLoggedInUserId(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).getId();
    }


    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bokning inte hittad med ID: " + id));
        return bookingMapper.toDTO(booking);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByCustomerId(userId);
    }


}