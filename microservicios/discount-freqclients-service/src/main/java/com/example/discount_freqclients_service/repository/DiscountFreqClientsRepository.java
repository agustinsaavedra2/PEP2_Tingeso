package com.example.discount_freqclients_service.repository;

import com.example.discount_freqclients_service.entity.DiscountFreqClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountFreqClientsRepository extends JpaRepository<DiscountFreqClientsEntity, Long> {

}
