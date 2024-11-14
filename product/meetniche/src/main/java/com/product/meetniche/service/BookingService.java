package com.product.meetniche.service;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(String username, BookingRequestDTO bookingRequestDTO);
    List<BookingResponseDTO> getBookingsForUser(String username);
}
