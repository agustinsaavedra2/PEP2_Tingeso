package com.example.booking_rate_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name="bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingEntity {

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
    @CollectionTable(name = "booking_clients", joinColumns = @JoinColumn(name = "booking_id"))
    private List<Long> clientIds;
}
