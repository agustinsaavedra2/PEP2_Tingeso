package com.example.discount_npeople_service.repository;

import com.example.discount_npeople_service.entity.DiscountNumPeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountNumPeopleRepository extends JpaRepository<DiscountNumPeopleEntity, Long> {
    public Optional<DiscountNumPeopleEntity> findById(Long id);
}
