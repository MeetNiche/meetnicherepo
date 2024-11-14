package com.product.meetniche.service;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(String username, BookingRequestDTO bookingRequestDTO);
    List<BookingResponseDTO> getBookingsForUser(String username);
    
    // New Methods for Booking Management
    BookingResponseDTO confirmBooking(Long bookingId, String username);
    BookingResponseDTO cancelBooking(Long bookingId, String username);
    BookingResponseDTO rescheduleBooking(Long bookingId, BookingRequestDTO newBookingDetails, String username);
}
