package com.example.booking_rate_service.repository;

import com.example.booking_rate_service.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    public Optional<BookingEntity> findById(Long id);
}
