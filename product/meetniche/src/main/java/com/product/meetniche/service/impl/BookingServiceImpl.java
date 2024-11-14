package com.product.meetniche.service.impl;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import com.product.meetniche.model.Booking;
import com.product.meetniche.model.BookingStatus;
import com.product.meetniche.model.User;
import com.product.meetniche.repository.BookingRepository;
import com.product.meetniche.repository.UserRepository;
import com.product.meetniche.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingResponseDTO createBooking(String username, BookingRequestDTO bookingRequestDTO) {
        User follower = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Follower not found"));

        User influencer = userRepository.findById(bookingRequestDTO.getInfluencerId())
            .orElseThrow(() -> new RuntimeException("Influencer not found"));

        Booking booking = new Booking();
        booking.setUser(follower);
        booking.setInfluencer(influencer);
        booking.setBookingDateTime(bookingRequestDTO.getBookingDateTime());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToDTO(savedBooking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsForUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUserOrInfluencer(user, user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setFollowerUsername(booking.getUser().getUsername());
        dto.setInfluencerUsername(booking.getInfluencer().getUsername());
        dto.setBookingDateTime(booking.getBookingDateTime());
        dto.setStatus(booking.getStatus().name());
        return dto;
    }
}
