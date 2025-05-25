package com.example.booking_voucher_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher")
public class VoucherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Integer numberLaps;
    private Integer maximumTime;
    private Integer numberPeople;
    private String bookingName;

    private String clientName;
    private Double base_price;
    private Double discountNumberPeople;
    private Double discountFrequentCustomer;
    private Double discountSpecialDays;
    private Double final_price;
    private Double iva;
    private Double total_price;
}