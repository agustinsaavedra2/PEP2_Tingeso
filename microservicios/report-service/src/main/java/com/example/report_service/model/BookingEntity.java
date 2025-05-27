package com.example.report_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {
    private Long id;
    private String nameBooking;
    private Integer lapsNumber;
    private Integer maximumTime;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private int totalDuration;
    private Double basePrice;
    private Double discountByPeopleNumber;
    private Double discountByFrequentCustomer;
    private Double discountBySpecialDays;
    private List<Long> clientIds;
}
