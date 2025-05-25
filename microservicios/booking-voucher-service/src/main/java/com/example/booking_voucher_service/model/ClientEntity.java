package com.example.booking_voucher_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    private Long id;
    private String name;
    private String rut;
    private String email;
    private LocalDate birthDate;
    private int numberOfVisits;
}
