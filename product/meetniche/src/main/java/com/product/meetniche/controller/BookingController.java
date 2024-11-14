package com.product.meetniche.controller;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import com.product.meetniche.service.BookingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponseDTO createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.createBooking(userDetails.getUsername(), bookingRequestDTO);
    }

    @GetMapping
    public List<BookingResponseDTO> getBookingsForUser(@AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.getBookingsForUser(userDetails.getUsername());
    }

    // New endpoints for booking management

    @PutMapping("/{bookingId}/confirm")
    public BookingResponseDTO confirmBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.confirmBooking(bookingId, userDetails.getUsername());
    }

    @PutMapping("/{bookingId}/cancel")
    public BookingResponseDTO cancelBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.cancelBooking(bookingId, userDetails.getUsername());
    }

    @PutMapping("/{bookingId}/reschedule")
    public BookingResponseDTO rescheduleBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingRequestDTO newBookingDetails,
            @AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.rescheduleBooking(bookingId, newBookingDetails, userDetails.getUsername());
    }
}
