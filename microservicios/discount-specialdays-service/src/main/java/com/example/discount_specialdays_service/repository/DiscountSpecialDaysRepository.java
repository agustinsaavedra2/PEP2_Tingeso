package com.example.discount_specialdays_service.repository;

import com.example.discount_specialdays_service.entity.DiscountSpecialDaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountSpecialDaysRepository extends JpaRepository<DiscountSpecialDaysEntity, Long> {
}
