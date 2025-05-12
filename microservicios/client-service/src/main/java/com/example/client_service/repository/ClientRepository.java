package com.example.client_service.repository;

import com.example.client_service.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    public Optional<ClientEntity> findById(Long id);
}
