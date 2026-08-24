package com.healthsync.ai.recommendation.service.impl;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.healthsync.ai.recommendation.service.GeminiAIService;

import reactor.util.retry.Retry;

@Service
public class GeminiAIServiceImpl implements GeminiAIService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.model}")
    private String geminiModel;

    public GeminiAIServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public String getRecommendations(String details) {

        Map<String, Object> requestBody = Map.of(
                "model", geminiModel,
                "input", details
        );

        return webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(ex ->
                                        ex instanceof WebClientResponseException.TooManyRequests
                                )
                )
                .block();
    }
}