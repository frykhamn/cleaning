package com.cleaning.service.mapper;

import com.cleaning.service.dto.BookingDTO;
import com.cleaning.service.entities.Booking;
import com.cleaning.service.enums.BookingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "cleaner.id", target = "cleanerId")
    @Mapping(source = "cleaningType.id", target = "cleaningTypeId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    BookingDTO toDto(Booking booking);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "cleanerId", target = "cleaner.id")
    @Mapping(source = "cleaningType.id", target = "cleaningTypeId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    Booking toEntity(BookingDTO bookingDTO);

    // Metoder för att hantera status som enum/string
    @Named("statusToString")
    default String statusToString(BookingStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToStatus")
    default BookingStatus stringToStatus(String status) {
        return status != null ? BookingStatus.valueOf(status) : null;
    }
}
