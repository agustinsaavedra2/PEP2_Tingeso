package com.example.discount_specialdays_service.service;

import com.example.discount_specialdays_service.model.BookingEntity;
import com.example.discount_specialdays_service.model.ClientEntity;
import com.example.discount_specialdays_service.repository.DiscountSpecialDaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountSpecialDaysService {

    @Autowired
    private DiscountSpecialDaysRepository discountSpecialDaysRepository;

    @Autowired
    private RestTemplate restTemplate;

    public BookingEntity findBookingById(Long id){
        return restTemplate.getForObject("http://booking-rate-service/api/booking/" + id, BookingEntity.class);
    }

    public ClientEntity findClientById(Long id){
        return restTemplate.getForObject("http://client-service/api/client/" + id, ClientEntity.class);
    }

    public void updateBooking(BookingEntity booking){
        String url = "http://booking-rate-service/api/booking/";
        restTemplate.put(url, booking);
    }

    public List<Pair<String, Double>> setDiscountBySpecialDays(Long id) {
        BookingEntity booking = findBookingById(id);
        LocalDate bookingDate = booking.getBookingDate();

        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(bookingDate.getYear(), 1, 1),
                LocalDate.of(bookingDate.getYear(), 4, 18),
                LocalDate.of(bookingDate.getYear(), 4, 19),
                LocalDate.of(bookingDate.getYear(), 5, 1),
                LocalDate.of(bookingDate.getYear(), 5, 21),
                LocalDate.of(bookingDate.getYear(), 6, 20),
                LocalDate.of(bookingDate.getYear(), 6, 29),
                LocalDate.of(bookingDate.getYear(), 7, 16),
                LocalDate.of(bookingDate.getYear(), 8, 15),
                LocalDate.of(bookingDate.getYear(), 9, 18),
                LocalDate.of(bookingDate.getYear(), 9, 19),
                LocalDate.of(bookingDate.getYear(), 10, 12),
                LocalDate.of(bookingDate.getYear(), 10, 31),
                LocalDate.of(bookingDate.getYear(), 11, 1),
                LocalDate.of(bookingDate.getYear(), 11, 16),
                LocalDate.of(bookingDate.getYear(), 12, 8),
                LocalDate.of(bookingDate.getYear(), 12, 14),
                LocalDate.of(bookingDate.getYear(), 12, 25)
        );

        Double newBasePrice = booking.getBasePrice();

        if (holidays.contains(bookingDate)) {
            newBasePrice += newBasePrice * 0.10;
        }
        if (bookingDate.getDayOfWeek() == DayOfWeek.SATURDAY || bookingDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            newBasePrice += newBasePrice * 0.15;
        }

        booking.setBasePrice(newBasePrice);

        int groupSize = booking.getClientIds().size();
        int numberBirthdays = 0;

        for (Long clientId : booking.getClientIds()) {
            ClientEntity client = findClientById(clientId);

            if (client != null && client.getBirthDate() != null) {
                LocalDate birthDate = client.getBirthDate();
                if (birthDate.getMonth() == bookingDate.getMonth() &&
                        birthDate.getDayOfMonth() == bookingDate.getDayOfMonth()) {
                    numberBirthdays++;
                }
            }
        }

        int numberPeopleDiscount = 0;
        if (groupSize >= 3 && groupSize <= 5) {
            numberPeopleDiscount = Math.min(1, numberBirthdays);
        } else if (groupSize >= 6 && groupSize <= 10) {
            numberPeopleDiscount = Math.min(2, numberBirthdays);
        }

        List<Pair<String, Double>> clientDiscountBySpecialDays = new ArrayList<>();
        double totalBirthdayDiscount = 0.0;
        int appliedDiscounts = 0;

        for (Long clientId : booking.getClientIds()) {
            ClientEntity client = findClientById(clientId);

            if (client == null) {
                throw new IllegalArgumentException("The client was not found");
            }

            double clientDiscount = 0.0;

            if (client.getBirthDate() != null) {
                LocalDate birthDate = client.getBirthDate();

                if (birthDate.getMonth() == bookingDate.getMonth() &&
                        birthDate.getDayOfMonth() == bookingDate.getDayOfMonth() &&
                        appliedDiscounts < numberPeopleDiscount) {

                    clientDiscount = booking.getBasePrice() * 0.50;
                    totalBirthdayDiscount += clientDiscount;
                    appliedDiscounts++;
                }
            }

            clientDiscountBySpecialDays.add(Pair.of(client.getName(), clientDiscount));
        }

        booking.setDiscountBySpecialDays(totalBirthdayDiscount);
        updateBooking(booking);

        return clientDiscountBySpecialDays;
    }
}
