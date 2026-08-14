package com.healthsync.user.user.service.impl;

import com.healthsync.user.api.v1.request.RegisterRequest;
import com.healthsync.user.api.v1.response.UserResponse;
import com.healthsync.user.exception.custom.EmailAlreadyExistsException;
import com.healthsync.user.exception.custom.UserNotFoundException;
import com.healthsync.user.user.entity.User;
import com.healthsync.user.user.mapper.UserMapper;
import com.healthsync.user.user.repository.UserRepository;
import com.healthsync.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {

        final String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "User already exists with email: " + email
            );
        }

        final User user = userMapper.toEntity(request, email);

        final User savedUser = userRepository.save(user);

        log.info("User registered successfully with id: {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserProfile(Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        return userMapper.toResponse(user);
    }

    @Override
    public boolean existsByUserId(Long userId) {

        return userRepository.existsById(userId);
    }
}