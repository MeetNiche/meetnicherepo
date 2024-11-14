package com.product.meetniche.controller;

import com.product.meetniche.dto.UserProfileDTO;
import com.product.meetniche.dto.UserProfileUpdateDTO;
import com.product.meetniche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to retrieve profile
    @GetMapping("/profile")
    public UserProfileDTO getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserProfile(userDetails.getUsername());
    }

    // Endpoint to update profile
    @PutMapping("/profile")
    public UserProfileDTO updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileUpdateDTO profileUpdateDTO) {
        return userService.updateUserProfile(userDetails.getUsername(), profileUpdateDTO);
    }
}
