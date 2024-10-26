package com.cleaning.service.services;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.entities.User;
import com.cleaning.service.mapper.BookingMapper;
import com.cleaning.service.repositories.BookingRepository;
import com.cleaning.service.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public BookingDTO createBooking(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking != null ? bookingMapper.toDto(savedBooking) : null;
    }

    public List<BookingDTO> getCustomerBookings(Long userId) {
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
    }

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByCustomerId(userId);
    }
}