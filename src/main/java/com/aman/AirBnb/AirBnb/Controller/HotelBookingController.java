package com.aman.AirBnb.AirBnb.Controller;

import com.aman.AirBnb.AirBnb.Dto.*;
import com.aman.AirBnb.AirBnb.Service.Interfaces.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDTO> initialiseBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDTO> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDTO> guestDtoList) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }

    @PostMapping("/{bookingId}/payments")
    @Operation(summary = "Initiate payments flow for the booking", tags = {"Booking Flow"})
    public ResponseEntity<BookingPaymentInitResponseDTO> initiatePayment(@PathVariable Long bookingId) {
        String sessionUrl = bookingService.initiatePayments(bookingId);
        return ResponseEntity.ok(new BookingPaymentInitResponseDTO(sessionUrl));
    }

    @PostMapping("/{bookingId}/cancel")
    @Operation(summary = "Cancel the booking", tags = {"Booking Flow"})
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookingId}/status")
    @Operation(summary = "Check the status of the booking", tags = {"Booking Flow"})
    public ResponseEntity<BookingStatusResponseDTO> getBookingStatus(@PathVariable Long bookingId) {
        return ResponseEntity.ok(new BookingStatusResponseDTO(bookingService.getBookingStatus(bookingId)));
    }
}
