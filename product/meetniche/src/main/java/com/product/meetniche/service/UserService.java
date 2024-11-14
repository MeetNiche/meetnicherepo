package com.product.meetniche.service;

import com.product.meetniche.dto.UserRegistrationDTO;
import com.product.meetniche.dto.UserProfileDTO;
import com.product.meetniche.dto.UserProfileUpdateDTO;
import com.product.meetniche.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(UserRegistrationDTO registrationDTO);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    
    // New methods for profile management
    UserProfileDTO getUserProfile(String username);
    UserProfileDTO updateUserProfile(String username, UserProfileUpdateDTO profileUpdateDTO);
}
