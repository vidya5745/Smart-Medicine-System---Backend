package com.smartmedicine.smartmedicine.controller;

import com.smartmedicine.smartmedicine.model.user;
import com.smartmedicine.smartmedicine.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {

        user user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));

        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        if (!user.getRole().equalsIgnoreCase(request.role())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong account type"
            );
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public record LoginRequest(
            String email,
            String password,
            String role
    ) {}

    public record UserResponse(
            Long id,
            String name,
            String email,
            String role
    ) {}
}