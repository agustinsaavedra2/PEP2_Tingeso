package com.example.booking_voucher_service.controller;

import com.example.booking_voucher_service.entity.VoucherEntity;
import com.example.booking_voucher_service.model.BookingEntity;
import com.example.booking_voucher_service.service.VoucherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
@AllArgsConstructor
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingEntity>> findAllBookings(){
        List<BookingEntity> bookings = voucherService.findAllBookings();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<List<VoucherEntity>> findVouchersByBookingId(@PathVariable("bookingId") Long bookingId){
        List<VoucherEntity> vouchers = voucherService.findVouchersByBookingId(bookingId);

        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/generateClientVouchers/{bookingId}")
    public ResponseEntity<List<VoucherEntity>> generateClientVouchers(@PathVariable("bookingId") Long bookingId){
        List<VoucherEntity> clientVouchers = voucherService.generateVouchers(bookingId);
        return ResponseEntity.ok(clientVouchers);
    }
}
