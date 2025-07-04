package com.example.discount_freqclients_service.controller;

import com.example.discount_freqclients_service.model.BookingEntity;
import com.example.discount_freqclients_service.model.ClientEntity;
import com.example.discount_freqclients_service.service.DiscountFreqClientsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/discountFreqClients")
public class DiscountFreqClientsController {

    @Autowired
    private DiscountFreqClientsService discountFreqClientsService;

    @PutMapping("/{id}")
    public ResponseEntity<List<Pair<String, Double>>> setDiscountByFrequentCustomer(@PathVariable("id") Long id){
        List<Pair<String, Double>> discountFrequentCustomer = discountFreqClientsService.setDiscountByFrequentCustomer(id);

        return ResponseEntity.ok(discountFrequentCustomer);
    }
}
