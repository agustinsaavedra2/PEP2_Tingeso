package com.example.booking_voucher_service.service;

import com.example.booking_voucher_service.entity.VoucherEntity;
import com.example.booking_voucher_service.model.BookingEntity;
import com.example.booking_voucher_service.model.ClientEntity;
import com.example.booking_voucher_service.repository.VoucherRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public VoucherEntity createVoucher(VoucherEntity voucher){
        return voucherRepository.save(voucher);
    }

    public VoucherEntity findById(Long id){
        return voucherRepository.findById(id).orElse(null);
    }

    public List<BookingEntity> findAllBookings(){
        return restTemplate.getForObject("http://booking-rate-service/api/booking/", List.class);
    }

    public BookingEntity findBookingById(Long id){
        return restTemplate.getForObject("http://booking-rate-service/api/booking/" + id, BookingEntity.class);
    }

    public ClientEntity findClientById(Long id){
        return restTemplate.getForObject("http://client-service/api/client/" + id, ClientEntity.class);
    }

    public List<VoucherEntity> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public VoucherEntity updateVoucher(VoucherEntity voucher){
        return voucherRepository.save(voucher);
    }

    public List<Pair<String, Double>> discountByNumberPeople(Long id) {
        String url = "http://discount-npeople-service/api/discountNumPeople/" + id;

        ResponseEntity<List<Pair<String, Double>>> discountNumberPeople = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<List<Pair<String, Double>>>() {}
        );

        return discountNumberPeople.getBody();
    }

    public List<Pair<String, Double>> discountByFrequentCustomer(Long id) {
        String url = "http://discount-freqclients-service/api/discountFreqClients/" + id;

        ResponseEntity<List<Pair<String, Double>>> discountFrequentCustomer = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<List<Pair<String, Double>>>() {}
        );

        return discountFrequentCustomer.getBody();
    }

    public List<Pair<String, Double>> discountBySpecialDays(Long id) {
        String url = "http://discount-specialdays-service/api/discountSpecialDays/" + id;

        ResponseEntity<List<Pair<String, Double>>> discountSpecialDays = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<List<Pair<String, Double>>>() {}
        );

        return discountSpecialDays.getBody();
    }

    public void deleteVoucher(Long id) throws Exception {
        try {
            voucherRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<VoucherEntity> generateVouchers(Long bookingId) {
        BookingEntity booking = findBookingById(bookingId);

        if (booking == null) {
            return null;
        }

        List<Pair<String, Double>> discountNumPeople = discountByNumberPeople(bookingId);

        Map<String, Double> specialDiscountNumPeopleMap = discountNumPeople.stream()
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        List<Pair<String, Double>> discountFrequentCustomer= discountByFrequentCustomer(bookingId);

        Map<String, Double> specialDiscountFrequentCusomerMap = discountFrequentCustomer.stream()
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        List<Pair<String, Double>> discountSpecials = discountBySpecialDays(bookingId);

        Map<String, Double> specialDiscountSpecialDaysMap = discountSpecials.stream()
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        List<VoucherEntity> vouchers = new ArrayList<>();

        for (Long clientId : booking.getClientIds()) {
            ClientEntity client = findClientById(clientId);

            double basePrice = booking.getBasePrice();

            double discountPeople = specialDiscountNumPeopleMap.getOrDefault(client.getName(), 0.0);
            double discountFrequent = specialDiscountFrequentCusomerMap.getOrDefault(client.getName(), 0.0);
            double discountSpecial = specialDiscountSpecialDaysMap.getOrDefault(client.getName(), 0.0);

            double finalPrice = basePrice - discountPeople - discountFrequent - discountSpecial;
            double iva = finalPrice * 0.19;
            double totalPrice = finalPrice + iva;

            VoucherEntity voucher = new VoucherEntity();
            voucher.setBookingId(booking.getId());
            voucher.setBookingDate(booking.getBookingDate());
            voucher.setBookingTime(booking.getBookingTime());
            voucher.setNumberLaps(booking.getLapsNumber());
            voucher.setMaximumTime(booking.getMaximumTime());
            voucher.setNumberPeople(booking.getClientIds().size());
            voucher.setBookingName(booking.getNameBooking());
            voucher.setClientName(client.getName());
            voucher.setBase_price(basePrice);
            voucher.setDiscountNumberPeople(discountPeople);
            voucher.setDiscountFrequentCustomer(discountFrequent);
            voucher.setDiscountSpecialDays(discountSpecial);
            voucher.setFinal_price(finalPrice);
            voucher.setIva(iva);
            voucher.setTotal_price(totalPrice);

            VoucherEntity saved = createVoucher(voucher);
            vouchers.add(saved);

            byte[] pdfBytes = generateVoucherPDF(saved);
            sendEmailWithVoucher(saved.getClientName(), username, pdfBytes);
        }

        return vouchers;
    }

    private byte[] generateVoucherPDF(VoucherEntity voucher) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Comprobante de Reserva", titleFont));
            document.add(new Paragraph(" ", bodyFont));

            document.add(new Paragraph("Cliente: " + voucher.getClientName(), bodyFont));
            document.add(new Paragraph("Reserva: " + voucher.getBookingName(), bodyFont));
            document.add(new Paragraph("Fecha: " + voucher.getBookingDate(), bodyFont));
            document.add(new Paragraph("Hora: " + voucher.getBookingTime(), bodyFont));
            document.add(new Paragraph("Número de Vueltas: " + voucher.getNumberLaps(), bodyFont));
            document.add(new Paragraph("Tiempo Máximo: " + voucher.getMaximumTime() + " minutos", bodyFont));
            document.add(new Paragraph("Precio Base: $" + voucher.getBase_price(), bodyFont));
            document.add(new Paragraph("Descuento por Personas: $" + voucher.getDiscountNumberPeople(), bodyFont));
            document.add(new Paragraph("Descuento Cliente Frecuente: $" + voucher.getDiscountFrequentCustomer(), bodyFont));
            document.add(new Paragraph("Descuento Días Especiales: $" + voucher.getDiscountSpecialDays(), bodyFont));
            document.add(new Paragraph("IVA: $" + voucher.getIva(), bodyFont));
            document.add(new Paragraph("Precio Final: $" + voucher.getFinal_price(), bodyFont));
            document.add(new Paragraph("Precio Total: $" + voucher.getTotal_price(), bodyFont));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendEmailWithVoucher(String clientName, String to, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true significa que podemos adjuntar archivos

            helper.setTo(to);
            helper.setSubject("Comprobante de Reserva para " + clientName);
            helper.setText("Adjunto encontrarás tu comprobante de reserva.");

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            helper.addAttachment("Voucher-" + clientName + ".pdf", resource);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo con el voucher adjunto", e);
        }
    }
}
