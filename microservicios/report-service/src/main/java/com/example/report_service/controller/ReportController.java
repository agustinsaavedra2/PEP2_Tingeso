package com.example.report_service.controller;

import com.example.report_service.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/revenueByType")
    public List<Map<String, Object>> getRevenueReportByBookingType(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportService.getRevenueReportByBookingType(startDate, endDate);
    }

    @GetMapping("/revenueByGroupSize")
    public List<Map<String, Object>> getRevenueReportByGroupSize(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportService.getRevenueReportByGroupSize(startDate, endDate);
    }
}
