package com.example.booking_rate_service.service;

import com.example.booking_rate_service.entity.BookingEntity;
import com.example.booking_rate_service.model.ClientEntity;
import com.example.booking_rate_service.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    public BookingEntity createBooking(BookingEntity bookingEntity) {
        return bookingRepository.save(bookingEntity);
    }

    public BookingEntity findBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<BookingEntity> findByBookingDateBetween(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findByBookingDateBetween(startDate, endDate);
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    public ClientEntity getClientById(Long id) {
        return restTemplate.getForObject("http://client-service/api/client/" + id, ClientEntity.class);
    }

    public BookingEntity updateBooking(BookingEntity booking){
        return bookingRepository.save(booking);
    }

    public void deleteBookingById(Long id) throws Exception{
        try{
            bookingRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception("Error to delete client with id: " + id);
        }
    }

    public List<Pair<String, Double>> setPriceAndDurationInBooking(Long id){
        BookingEntity booking = findBookingById(id);

        if(booking == null || booking.getClientIds() == null || booking.getClientIds().isEmpty()){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(booking.getLapsNumber() == 10 ||
                (booking.getMaximumTime() > 0 && booking.getMaximumTime() <= 10)){
            booking.setBasePrice(15000.0);
            booking.setTotalDuration(30);
        }

        else if(booking.getLapsNumber() == 15 ||
                (booking.getMaximumTime() > 10 && booking.getMaximumTime() <= 15)){
            booking.setBasePrice(20000.0);
            booking.setTotalDuration(35);
        }

        else if(booking.getLapsNumber() == 20 ||
                (booking.getMaximumTime() > 15 && booking.getMaximumTime() <= 20)){
            booking.setBasePrice(25000.0);
            booking.setTotalDuration(40);
        }
        else{
            throw new IllegalArgumentException("The maximum time or the number of laps are out of range");
        }

        List<Pair<String, Double>> clientsBasePrice = new ArrayList<>();

        for(Long clientId: booking.getClientIds()){
            ClientEntity client = getClientById(clientId);

            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }

            clientsBasePrice.add(Pair.of(client.getName(), booking.getBasePrice()));
        }

        updateBooking(booking);

        return clientsBasePrice;
    }
}
