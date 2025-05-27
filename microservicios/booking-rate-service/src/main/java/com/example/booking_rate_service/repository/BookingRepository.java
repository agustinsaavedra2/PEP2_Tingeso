package com.example.booking_rate_service.repository;

import com.example.booking_rate_service.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    public Optional<BookingEntity> findById(Long id);
    public List<BookingEntity> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);
}
