package com.product.meetniche.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {

    private Long influencerId;  // ID of the influencer being booked
    private LocalDateTime bookingDateTime; // Date and time requested for the booking
}
