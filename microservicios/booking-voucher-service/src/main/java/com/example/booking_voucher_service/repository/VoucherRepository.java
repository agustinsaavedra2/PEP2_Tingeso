package com.example.booking_voucher_service.repository;

import com.example.booking_voucher_service.entity.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {
    public Optional<VoucherEntity> findById(Long id);
}
