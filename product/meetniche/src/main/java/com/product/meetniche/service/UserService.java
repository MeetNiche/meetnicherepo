package com.product.meetniche.service;

import com.product.meetniche.dto.UserRegistrationDTO;
import com.product.meetniche.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(UserRegistrationDTO registrationDTO);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
