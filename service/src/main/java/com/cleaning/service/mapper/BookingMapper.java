// src/main/java/com/cleaning/service/mapper/BookingMapper.java
package com.cleaning.service.mapper;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.dto.CreateBookingRequest;
import com.cleaning.service.entities.Address;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.entities.CleaningType;
import com.cleaning.service.entities.User;
import com.cleaning.service.repositories.AddressRepository;
import com.cleaning.service.repositories.CleaningTypeRepository;
import com.cleaning.service.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookingMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CleaningTypeRepository cleaningTypeRepository;

    @Autowired
    protected AddressRepository addressRepository;

    // Mappa Booking till BookingDTO
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "cleaner.id", target = "cleanerId")
    @Mapping(source = "cleaningType.id", target = "cleaningTypeId")
    @Mapping(source = "address.id", target = "addressId")
    public abstract BookingDTO toDTO(Booking booking);

    // Mappa CreateBookingRequest till Booking entitet
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "cleaner", ignore = true)
    @Mapping(target = "cleaningType", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Booking toEntity(CreateBookingRequest request);

    // Eftermappning för att sätta relaterade entiteter
    @AfterMapping
    protected void setEntities(CreateBookingRequest request, @MappingTarget Booking booking) {
        if (request.getCustomerId() != null) {
            User customer = userRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Kund inte hittad med ID: " + request.getCustomerId()));
            booking.setCustomer(customer);
        }

        if (request.getCleanerId() != null) {
            User cleaner = userRepository.findById(request.getCleanerId())
                    .orElseThrow(() -> new EntityNotFoundException("Städare inte hittad med ID: " + request.getCleanerId()));
            booking.setCleaner(cleaner);
        }

        if (request.getCleaningTypeId() != null) {
            CleaningType cleaningType = cleaningTypeRepository.findById(request.getCleaningTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Städningstyp inte hittad med ID: " + request.getCleaningTypeId()));
            booking.setCleaningType(cleaningType);
        }

        if (request.getAddressId() != null) {
            Address address = addressRepository.findById(request.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Adress inte hittad med ID: " + request.getAddressId()));
            booking.setAddress(address);
        }
    }
}
