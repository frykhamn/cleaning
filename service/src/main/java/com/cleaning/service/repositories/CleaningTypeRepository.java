package com.cleaning.service.repositories;

import com.cleaning.service.entities.CleaningType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleaningTypeRepository extends JpaRepository<CleaningType, Long> {
}
