package com.healthsync.ai.recommendation.service.impl;

import java.util.Map;
import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import com.healthsync.ai.recommendation.service.GeminiAIService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiAIServiceImpl implements GeminiAIService {
    private final WebClient webClient;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiAIServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public String getRecommendations(String details) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", details)
                        })
                }
        );

//        String response = webClient.post()
        /*return webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();*/
        return webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
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
