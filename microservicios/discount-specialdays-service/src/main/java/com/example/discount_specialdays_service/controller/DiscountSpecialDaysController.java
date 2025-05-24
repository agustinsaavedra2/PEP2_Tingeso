package com.example.discount_specialdays_service.controller;

import com.example.discount_specialdays_service.service.DiscountSpecialDaysService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/discountSpecialDays")
@AllArgsConstructor
public class DiscountSpecialDaysController {

    @Autowired
    DiscountSpecialDaysService discountSpecialDaysService;

    @PutMapping("/{id}")
    public ResponseEntity<List<Pair<String,Double>>> setDiscountBySpecialDays(@PathVariable("id") Long id){
        List<Pair<String,Double>> discountSpecialDays = discountSpecialDaysService.setDiscountBySpecialDays(id);

        return ResponseEntity.ok(discountSpecialDays);
    }
}
