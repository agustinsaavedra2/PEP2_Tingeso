package com.example.weekly_rack_service.controller;

import com.example.weekly_rack_service.model.BookingEntity;
import com.example.weekly_rack_service.service.RackService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rack")
public class RackController {

    @Autowired
    private RackService rackService;

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyRacks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            LocalDate startDate){
        Map<String, Map<LocalTime, BookingEntity>> rack = rackService.getWeeklyBookingRackFromDate(startDate);

        return ResponseEntity.ok(rack);
    }

}
