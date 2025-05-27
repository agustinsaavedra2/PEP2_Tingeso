package com.example.weekly_rack_service.service;

import com.example.weekly_rack_service.entity.RackEntity;
import com.example.weekly_rack_service.model.BookingEntity;
import com.example.weekly_rack_service.repository.RackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class RackService {

    @Autowired
    private RackRepository rackRepository;

    @Autowired
    private RestTemplate restTemplate;

    public RackEntity createRack(RackEntity rack){
        return rackRepository.save(rack);
    }

    public List<BookingEntity> findByBookingDateBetween(LocalDate startDate, LocalDate endDate) {
        String url = String.format("http://booking-rate-service/api/booking/between-dates?startDate=%s&endDate=%s",
                startDate.toString(), endDate.toString());

        ResponseEntity<List<BookingEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookingEntity>>() {}
        );

        return response.getBody();
    }

    public static LocalTime getStartTimeForDate(LocalDate date, List<LocalDate> holidays) {
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || holidays.contains(date)) {
            return LocalTime.of(10, 0);
        } else {
            return LocalTime.of(14, 0);
        }
    }

    public Map<String, Map<LocalTime, BookingEntity>> getWeeklyBookingRackFromDate(LocalDate startDate) {
        Map<String, Map<LocalTime, BookingEntity>> rack = new LinkedHashMap<>();

        int slotMinutes = 20;
        LocalDate monday = startDate.with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);

        // Lista de feriados
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(startDate.getYear(), 1, 1),
                LocalDate.of(startDate.getYear(), 4, 18),
                LocalDate.of(startDate.getYear(), 4, 19),
                LocalDate.of(startDate.getYear(), 5, 1),
                LocalDate.of(startDate.getYear(), 5, 21),
                LocalDate.of(startDate.getYear(), 6, 20),
                LocalDate.of(startDate.getYear(), 6, 29),
                LocalDate.of(startDate.getYear(), 7, 16),
                LocalDate.of(startDate.getYear(), 8, 15),
                LocalDate.of(startDate.getYear(), 9, 18),
                LocalDate.of(startDate.getYear(), 9, 19),
                LocalDate.of(startDate.getYear(), 10, 12),
                LocalDate.of(startDate.getYear(), 10, 31),
                LocalDate.of(startDate.getYear(), 11, 1),
                LocalDate.of(startDate.getYear(), 11, 16),
                LocalDate.of(startDate.getYear(), 12, 8),
                LocalDate.of(startDate.getYear(), 12, 14),
                LocalDate.of(startDate.getYear(), 12, 25)
        );

        // Buscar reservas entre lunes y domingo
        List<BookingEntity> weekBookings = findByBookingDateBetween(monday, sunday);

        // Indexar por fecha y hora
        Map<LocalDate, Map<LocalTime, BookingEntity>> bookingMap = new HashMap<>();
        for (BookingEntity booking : weekBookings) {
            bookingMap
                    .computeIfAbsent(booking.getBookingDate(), k -> new HashMap<>())
                    .put(booking.getBookingTime(), booking);
        }

        // Construir mapa día por día
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            String dayName = currentDate.getDayOfWeek().toString();
            Map<LocalTime, BookingEntity> slots = new LinkedHashMap<>();

            LocalTime startTime = getStartTimeForDate(currentDate, holidays);
            LocalTime endTime = LocalTime.of(22, 0);

            for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(slotMinutes)) {
                BookingEntity bookingSlot = bookingMap
                        .getOrDefault(currentDate, Collections.emptyMap())
                        .getOrDefault(time, null);

                slots.put(time, bookingSlot); // null = libre
            }

            rack.put(dayName, slots);
        }

        return rack;
    }
}
