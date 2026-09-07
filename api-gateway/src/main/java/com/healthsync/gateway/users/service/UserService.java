package com.healthsync.gateway.users.service;

import com.healthsync.gateway.api.request.RegisterRequest;
import com.healthsync.gateway.api.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {

        log.info("Calling User Service for {}", userId);

        return userServiceWebClient.get()
                .uri("/api/v1/users/{userId}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(user -> true)
                .onErrorResume(
                        WebClientResponseException.NotFound.class,
                        ex -> {
                            log.info("User not found: {}", userId);
                            return Mono.just(false);
                        }
                );
    }

    public Mono<UserResponse> registerUser(
            RegisterRequest registerRequest
    ) {

        log.info(
                "Calling User Registration for {}",
                registerRequest.getEmail()
        );

        return userServiceWebClient.post()
                .uri("/api/v1/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(
                        WebClientResponseException.class,
                        ex -> {

                            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                                return Mono.error(
                                        new RuntimeException(
                                                "Bad request : " + ex.getMessage()
                                        )
                                );
                            }

                            return Mono.error(
                                    new RuntimeException(
                                            "Unexpected error : " + ex.getMessage()
                                    )
                            );
                        }
                );
    }
}

