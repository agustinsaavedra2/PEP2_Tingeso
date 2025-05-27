package com.example.report_service.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VoucherEntity {
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