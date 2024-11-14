package com.product.meetniche.service.impl;

import com.product.meetniche.dto.BookingRequestDTO;
import com.product.meetniche.dto.BookingResponseDTO;
import com.product.meetniche.exception.NotFoundException;
import com.product.meetniche.exception.UnauthorizedException;
import com.product.meetniche.model.Booking;
import com.product.meetniche.model.BookingStatus;
import com.product.meetniche.model.User;
import com.product.meetniche.repository.BookingRepository;
import com.product.meetniche.repository.UserRepository;
import com.product.meetniche.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Creating a new booking for user: {}", username);

        User follower = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                log.error("Follower not found with username: {}", username);
                return new NotFoundException("Follower not found");
            });

        User influencer = userRepository.findById(bookingRequestDTO.getInfluencerId())
            .orElseThrow(() -> {
                log.error("Influencer not found with ID: {}", bookingRequestDTO.getInfluencerId());
                return new NotFoundException("Influencer not found");
            });

        Booking booking = new Booking();
        booking.setUser(follower);
        booking.setInfluencer(influencer);
        booking.setBookingDateTime(bookingRequestDTO.getBookingDateTime());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with ID: {}", savedBooking.getId());

        return mapToDTO(savedBooking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsForUser(String username) {
        log.info("Retrieving bookings for user: {}", username);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                log.error("User not found with username: {}", username);
                return new RuntimeException("User not found");
            });

        List<Booking> bookings = bookingRepository.findByUserOrInfluencer(user, user);
        log.info("Found {} bookings for user: {}", bookings.size(), username);

        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
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
    
    @Override
    public BookingResponseDTO confirmBooking(Long bookingId, String username) {
        log.info("Confirming booking with ID: {} by user: {}", bookingId, username);
        Booking booking = getBookingForUser(bookingId, username);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        log.info("Booking confirmed with ID: {}", bookingId);
        return mapToDTO(booking);
    }

    @Override
    public BookingResponseDTO cancelBooking(Long bookingId, String username) {
        log.info("Canceling booking with ID: {} by user: {}", bookingId, username);
        Booking booking = getBookingForUser(bookingId, username);
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        log.info("Booking canceled with ID: {}", bookingId);
        return mapToDTO(booking);
    }

    @Override
    public BookingResponseDTO rescheduleBooking(Long bookingId, BookingRequestDTO newBookingDetails, String username) {
        log.info("Rescheduling booking with ID: {} by user: {}", bookingId, username);
        Booking booking = getBookingForUser(bookingId, username);
        booking.setBookingDateTime(newBookingDetails.getBookingDateTime());
        booking.setStatus(BookingStatus.PENDING); // Reset status to pending after rescheduling
        bookingRepository.save(booking);
        log.info("Booking rescheduled with ID: {}", bookingId);
        return mapToDTO(booking);
    }

    private Booking getBookingForUser(Long bookingId, String username) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));

        if (!booking.getUser().getUsername().equals(username) && !booking.getInfluencer().getUsername().equals(username)) {
            log.error("Unauthorized access attempt by user: {} for booking ID: {}", username, bookingId);
            throw new UnauthorizedException("User is not authorized to manage this booking.");
        }

        return booking;
    }

}
