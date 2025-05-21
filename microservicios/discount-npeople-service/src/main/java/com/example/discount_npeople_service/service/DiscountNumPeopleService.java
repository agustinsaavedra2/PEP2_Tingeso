package com.example.discount_npeople_service.service;

import com.example.discount_npeople_service.entity.DiscountNumPeopleEntity;
import com.example.discount_npeople_service.model.BookingEntity;
import com.example.discount_npeople_service.model.ClientEntity;
import com.example.discount_npeople_service.repository.DiscountNumPeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountNumPeopleService {

    @Autowired
    private DiscountNumPeopleRepository discountNumPeopleRepository;

    @Autowired
    private RestTemplate restTemplate;

    public BookingEntity findBookingById(Long id) {
        return restTemplate.getForObject("http://booking-rate-service/api/booking/" + id, BookingEntity.class);
    }

    public ClientEntity findClientById(Long id) {
        return restTemplate.getForObject("http://client-service/api/client/" + id, ClientEntity.class);
    }

    public DiscountNumPeopleEntity getDiscountNumPeopleById(Long id){
        return discountNumPeopleRepository.findById(id).orElse(null);
    }

    public DiscountNumPeopleEntity updateDiscountNumPeople(DiscountNumPeopleEntity discountNumPeopleEntity){
        return discountNumPeopleRepository.save(discountNumPeopleEntity);
    }

    public List<Pair<String, Double>> setDiscountByPeopleNumber(Long id){
        BookingEntity booking = findBookingById(id);
        double discount = 0.0;

        if(booking == null || booking.getClientIds() == null || booking.getClientIds().isEmpty()){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(booking.getClientIds().size() >= 1 && booking.getClientIds().size() <= 2){
            discount = 0.0;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClientIds().size() >= 3 && booking.getClientIds().size() <= 5){
            discount = booking.getBasePrice() * 0.10;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClientIds().size() >= 6 && booking.getClientIds().size() <= 10){
            discount = booking.getBasePrice() * 0.20;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClientIds().size() >= 11 && booking.getClientIds().size() <= 15){
            discount = booking.getBasePrice() * 0.30;
            booking.setDiscountByPeopleNumber(discount);
        }
        else{
            throw new IllegalArgumentException("Client's size are not in range");
        }

        List<Pair<String, Double>> clientsDiscountSizePeople = new ArrayList<>();

        for(Long clientId: booking.getClientIds()){
            if(clientId == null){
                throw new IllegalArgumentException("The client was not found");
            }

            ClientEntity client = findClientById(clientId);
            clientsDiscountSizePeople.add(Pair.of(client.getName(), booking.getDiscountByPeopleNumber()));
        }

        DiscountNumPeopleEntity discountEntity = new DiscountNumPeopleEntity();

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

        updateDiscountNumPeople(discountEntity);

        return clientsDiscountSizePeople;
    }
}
