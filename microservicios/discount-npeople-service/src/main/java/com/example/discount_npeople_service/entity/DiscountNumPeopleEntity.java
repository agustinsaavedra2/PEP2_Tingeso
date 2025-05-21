package com.example.discount_npeople_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountNumPeopleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ElementCollection
    private List<Long> clientIds;
}
