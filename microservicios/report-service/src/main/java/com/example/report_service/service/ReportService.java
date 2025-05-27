package com.example.report_service.service;

import com.example.report_service.model.BookingEntity;
import com.example.report_service.model.VoucherEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportService {

    @Autowired
    private RestTemplate restTemplate;

    public List<VoucherEntity> findVouchersByBookingId(Long bookingId) {
        return restTemplate.getForObject("https://booking-voucher-service/api/voucher/" + bookingId, List.class);
    }

    public List<BookingEntity> findByBookingDateBetween(LocalDate startDate, LocalDate endDate) {
        String url = String.format("http://booking-service/api/booking/between-dates?startDate=%s&endDate=%s",
                startDate.toString(), endDate.toString());

        BookingEntity[] response = restTemplate.getForObject(url, BookingEntity[].class);
        return Arrays.asList(response);
    }

    public List<Map<String, Object>> getRevenueReportByBookingType(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> bookings = findByBookingDateBetween(startDate, endDate);

        Map<String, Map<String, Object>> reportMap = new HashMap<>();

        for (BookingEntity booking : bookings) {
            String type;

            int minValue = Integer.MAX_VALUE;

            if (booking.getLapsNumber() != null) {
                minValue = Math.min(minValue, booking.getLapsNumber());
            }
            if (booking.getMaximumTime() != null) {
                minValue = Math.min(minValue, booking.getMaximumTime());
            }

            if (minValue <= 10) {
                type = "10 laps or max 10 min";
            } else if (minValue <= 15) {
                type = "15 laps or max 15 min";
            } else if (minValue <= 20) {
                type = "20 laps or max 20 min";
            } else {
                continue;
            }

            List<VoucherEntity> vouchers = findVouchersByBookingId(booking.getId());

            double revenue = vouchers.stream()
                    .mapToDouble(v -> v.getTotal_price() != null ? v.getTotal_price() : 0.0)
                    .sum();

            if (!reportMap.containsKey(type)) {
                Map<String, Object> info = new HashMap<>();
                info.put("type", type);
                info.put("totalBookings", 0);
                info.put("totalRevenue", 0.0);
                reportMap.put(type, info);
            }

            Map<String, Object> report = reportMap.get(type);
            report.put("totalBookings", (int) report.get("totalBookings") + 1);
            report.put("totalRevenue", (double) report.get("totalRevenue") + revenue);
        }

        return new ArrayList<>(reportMap.values());
    }


    public List<Map<String, Object>> getRevenueReportByGroupSize(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> bookings = findByBookingDateBetween(startDate, endDate);

        Map<String, Map<String, Object>> reportMap = new HashMap<>();

        for (BookingEntity booking : bookings) {
            int groupSize = (booking.getClientIds() != null) ? booking.getClientIds().size() : 0;

            String range;
            if (groupSize >= 1 && groupSize <= 2) {
                range = "1-2 people";
            } else if (groupSize >= 3 && groupSize <= 5) {
                range = "3-5 people";
            } else if (groupSize >= 6 && groupSize <= 10) {
                range = "6-10 people";
            } else if (groupSize >= 11 && groupSize <= 15) {
                range = "11-15 people";
            }else{
                range = "Other";
            }

            List<VoucherEntity> vouchers = findVouchersByBookingId(booking.getId());

            double revenue = vouchers.stream()
                    .mapToDouble(v -> v.getTotal_price() != null ? v.getTotal_price() : 0.0)
                    .sum();

            if (!reportMap.containsKey(range)) {
                Map<String, Object> data = new HashMap<>();
                data.put("groupRange", range);
                data.put("totalBookings", 0);
                data.put("totalRevenue", 0.0);
                reportMap.put(range, data);
            }

            Map<String, Object> report = reportMap.get(range);
            report.put("totalBookings", (int) report.get("totalBookings") + 1);
            report.put("totalRevenue", (double) report.get("totalRevenue") + revenue);
        }

        return new ArrayList<>(reportMap.values());
    }
}
