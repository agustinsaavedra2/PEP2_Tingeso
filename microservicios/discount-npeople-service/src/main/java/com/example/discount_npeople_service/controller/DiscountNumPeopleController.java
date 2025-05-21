package com.example.discount_npeople_service.controller;

import com.example.discount_npeople_service.model.BookingEntity;
import com.example.discount_npeople_service.model.ClientEntity;
import com.example.discount_npeople_service.service.DiscountNumPeopleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/discountNumPeople")
@AllArgsConstructor
public class DiscountNumPeopleController {

    @Autowired
    DiscountNumPeopleService discountNumPeopleService;

    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingEntity> findBookingById(@PathVariable("id") Long id){
        BookingEntity booking = discountNumPeopleService.findBookingById(id);

        if(booking == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientEntity> findClientById(@PathVariable("id") Long id){
        ClientEntity client = discountNumPeopleService.findClientById(id);

        if(client == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Pair<String, Double>>> setDiscountByPeopleNumber(@PathVariable("id") Long id){
        List<Pair<String, Double>> discountPeopleNumber = discountNumPeopleService.setDiscountByPeopleNumber(id);

        return ResponseEntity.ok(discountPeopleNumber);
    }
}
