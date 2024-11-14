package com.product.meetniche.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String username;
    private String email;
    private String bio;
    private String profilePicture;
    private String role;
}
