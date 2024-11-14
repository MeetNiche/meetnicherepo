package com.product.meetniche.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The follower who is booking the session

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "influencer_id", nullable = false)
    private User influencer; // The influencer with whom the session is booked

    @Column(nullable = false)
    private LocalDateTime bookingDateTime; // Date and time of the booking

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // Status of the booking (e.g., PENDING, CONFIRMED, CANCELED)
}

