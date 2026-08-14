package com.healthsync.user.api.v1.controller;

import com.healthsync.user.api.v1.request.RegisterRequest;
import com.healthsync.user.api.v1.response.UserResponse;
import com.healthsync.user.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(
            @PathVariable Long userId) {

        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(userService.existsByUserId(userId));
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "Ok";
    }
}