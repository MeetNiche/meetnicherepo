package com.product.meetniche.service.impl;

import com.product.meetniche.dto.UserRegistrationDTO;
import com.product.meetniche.model.User;
import com.product.meetniche.repository.UserRepository;
import com.product.meetniche.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserRegistrationDTO registrationDTO) {
        log.info("Registering a new user with username: {}", registrationDTO.getUsername());
        
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(User.Role.valueOf(registrationDTO.getRole().toUpperCase()));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("Searching for user with username: {}", username);
        
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.warn("User not found with username: {}", username);
        }

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Searching for user with email: {}", email);
        
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.warn("User not found with email: {}", email);
        }

        return user;
    }
}
