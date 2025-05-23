package com.example.discount_freqclients_service.service;

import com.example.discount_freqclients_service.entity.DiscountFreqClientsEntity;
import com.example.discount_freqclients_service.model.BookingEntity;
import com.example.discount_freqclients_service.model.ClientEntity;
import com.example.discount_freqclients_service.repository.DiscountFreqClientsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountFreqClientsService {

    @Autowired
    private DiscountFreqClientsRepository discountFreqClientsRepository;

    @Autowired
    private RestTemplate restTemplate;

    public BookingEntity findBookingById(Long id) {
        return restTemplate.getForObject("http://booking-rate-service/api/booking/" + id, BookingEntity.class);
    }

    public ClientEntity findClientById(Long id) {
        return restTemplate.getForObject("http://client-service/api/client/" + id, ClientEntity.class);
    }

    public void updateBooking(BookingEntity booking) {
        String url = "http://booking-rate-service/api/booking/";
        restTemplate.put(url, booking);
    }

    public DiscountFreqClientsEntity updateDiscountFreqClients(DiscountFreqClientsEntity discountFreqClientsEntity){
        return discountFreqClientsRepository.save(discountFreqClientsEntity);
    }

    public List<Pair<String, Double>> setDiscountByFrequentCustomer(Long id) {
        BookingEntity booking = findBookingById(id);
        double discount = 0.0;

        if(booking == null || booking.getClientIds() == null || booking.getClientIds().isEmpty()) {
            throw new IllegalArgumentException("The booking was not found");
        }

        List<Pair<String, Double>> clientsDiscountFrequency = new ArrayList<>();

        for (Long clientId : booking.getClientIds()) {
            if(clientId == null) {
                throw new IllegalArgumentException("The client was not found");
            }

            ClientEntity client = findClientById(clientId);

            if (client.getNumberOfVisits() >= 0 && client.getNumberOfVisits() <= 1) {
                discount = booking.getBasePrice() * 0.00;
            }

            else if (client.getNumberOfVisits() >= 2 && client.getNumberOfVisits() <= 4) {
                discount = booking.getBasePrice() * 0.10;
            }

            else if (client.getNumberOfVisits() >= 5 && client.getNumberOfVisits() <= 6) {
                discount = booking.getBasePrice() * 0.20;
            }

            else if (client.getNumberOfVisits() >= 7) {
                discount = booking.getBasePrice() * 0.30;
            }

            clientsDiscountFrequency.add(Pair.of(client.getName(), discount));
        }

        booking.setDiscountByFrequentCustomer(discount);
        updateBooking(booking);

        DiscountFreqClientsEntity discountEntity = new DiscountFreqClientsEntity();

        discountEntity.setId(booking.getId());
        discountEntity.setNameBooking("Booking " + booking.getId());
        discountEntity.setLapsNumber(booking.getLapsNumber());
        discountEntity.setMaximumTime(booking.getMaximumTime());
        discountEntity.setBookingDate(booking.getBookingDate());
        discountEntity.setBookingTime(booking.getBookingTime());
        discountEntity.setTotalDuration(booking.getTotalDuration());
        discountEntity.setBasePrice(booking.getBasePrice());
        discountEntity.setDiscountByPeopleNumber(discount);
        discountEntity.setDiscountByFrequentCustomer(booking.getDiscountByFrequentCustomer());
        discountEntity.setDiscountBySpecialDays(booking.getDiscountBySpecialDays());
        discountEntity.setClientIds(booking.getClientIds());

        updateDiscountFreqClients(discountEntity);

        return clientsDiscountFrequency;
    }
}