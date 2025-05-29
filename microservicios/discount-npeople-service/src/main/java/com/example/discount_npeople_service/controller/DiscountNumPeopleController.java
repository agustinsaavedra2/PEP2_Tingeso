package com.example.discount_npeople_service.controller;

import com.example.discount_npeople_service.service.DiscountNumPeopleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/discountNumPeople")
@AllArgsConstructor
public class DiscountNumPeopleController {

    @Autowired
    DiscountNumPeopleService discountNumPeopleService;

    @PutMapping("/{id}")
    public ResponseEntity<List<Pair<String, Double>>> setDiscountByPeopleNumber(@PathVariable("id") Long id){
        List<Pair<String, Double>> discountPeopleNumber = discountNumPeopleService.setDiscountByPeopleNumber(id);

        return ResponseEntity.ok(discountPeopleNumber);
    }
}
