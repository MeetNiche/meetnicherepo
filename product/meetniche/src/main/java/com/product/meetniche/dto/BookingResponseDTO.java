package com.product.meetniche.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {

    private Long id;
    private String followerUsername;
    private String influencerUsername;
    private LocalDateTime bookingDateTime;
    private String status;
}
