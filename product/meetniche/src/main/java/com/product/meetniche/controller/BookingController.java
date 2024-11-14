package com.product.meetniche.controller;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import com.product.meetniche.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('FOLLOWER')")
    @PostMapping
    public BookingResponseDTO createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.createBooking(userDetails.getUsername(), bookingRequestDTO);
    }

    @PreAuthorize("hasAnyRole('FOLLOWER', 'INFLUENCER')")
    @GetMapping
    public List<BookingResponseDTO> getBookingsForUser(@AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.getBookingsForUser(userDetails.getUsername());
    }
}
