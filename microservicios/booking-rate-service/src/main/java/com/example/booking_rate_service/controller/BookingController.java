package com.example.booking_rate_service.controller;

import com.example.booking_rate_service.entity.BookingEntity;
import com.example.booking_rate_service.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/booking")
@AllArgsConstructor
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/")
    public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingEntity booking) {
        BookingEntity newBooking = bookingService.createBooking(booking);

        if(newBooking == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(newBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> findBookingById(@PathVariable("id") Long id){
        BookingEntity booking = bookingService.findBookingById(id);

        if(booking == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<BookingEntity>> findByBookingDateBetween( @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
            List<BookingEntity> bookings = bookingService.findByBookingDateBetween(startDate, endDate);

            if(bookings == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(bookings);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookingEntity>> getAllBookings(){
        List<BookingEntity> bookings = bookingService.getAllBookings();

        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/")
    public ResponseEntity<BookingEntity> updateBooking(@RequestBody BookingEntity booking) {
        BookingEntity updatedBooking = bookingService.updateBooking(booking);

        if(updatedBooking == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedBooking);
    }

    @PutMapping("/setPriceDuration/{id}")
    public ResponseEntity<List<Pair<String, Double>>> setPriceDuration(@PathVariable("id") Long id){
        List<Pair<String, Double>> clientsBasePrice = bookingService.setPriceAndDurationInBooking(id);

        return ResponseEntity.ok(clientsBasePrice);
    }
}
