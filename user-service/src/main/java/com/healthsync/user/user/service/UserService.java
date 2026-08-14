package com.healthsync.user.user.service;

import com.healthsync.user.api.v1.request.RegisterRequest;
import com.healthsync.user.api.v1.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse getUserProfile(Long userId);

    boolean existsByUserId(Long userId);
}