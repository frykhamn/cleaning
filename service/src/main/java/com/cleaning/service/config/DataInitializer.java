/*
package com.cleaning.service.config;

import com.cleaning.service.entities.Address;
import com.cleaning.service.entities.CleaningType;
import com.cleaning.service.entities.User;
import com.cleaning.service.enums.UserRole;
import com.cleaning.service.repositories.AddressRepository;
import com.cleaning.service.repositories.CleaningTypeRepository;
import com.cleaning.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CleaningTypeRepository cleaningTypeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Om du använder password encoding

    @Override
    public void run(String... args) throws Exception {
        // Infoga CleaningTypes
        CleaningType standardCleaning = new CleaningType();
        standardCleaning.setName("Standardstädning");
        standardCleaning.setDescription("En vanlig städning som utförs regelbundet");
        standardCleaning.setPrice(BigDecimal.valueOf(500));
        cleaningTypeRepository.save(standardCleaning);

        CleaningType moveOutCleaning = new CleaningType();
        moveOutCleaning.setName("Flyttstädning");
        moveOutCleaning.setDescription("Städning vid utflyttning");
        moveOutCleaning.setPrice(BigDecimal.valueOf(1000));
        cleaningTypeRepository.save(moveOutCleaning);

        CleaningType deepCleaning = new CleaningType();
        deepCleaning.setName("Storstädning");
        deepCleaning.setDescription("En noggrann städning av hela bostaden");
        deepCleaning.setPrice(BigDecimal.valueOf(1500));
        cleaningTypeRepository.save(deepCleaning);

        // Infoga Addresses
        Address address1 = new Address();
        address1.setStreet("Storgatan 1");
        address1.setCity("Stockholm");
        address1.setPostalCode("11122");
        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setStreet("Lillgatan 2");
        address2.setCity("Göteborg");
        address2.setPostalCode("41133");
        addressRepository.save(address2);

        // Infoga Users (Cleaners)
        User cleaner1 = new User();
        cleaner1.setUsername("cleaner1");
        cleaner1.setPassword(passwordEncoder.encode("password"));
        cleaner1.setUserRole(UserRole.CLEANER);
        userRepository.save(cleaner1);

        User cleaner2 = new User();
        cleaner2.setUsername("cleaner2");
        cleaner2.setPassword(passwordEncoder.encode("password"));
        cleaner2.setUserRole(UserRole.CLEANER);
        userRepository.save(cleaner2);

    }
}
*/
