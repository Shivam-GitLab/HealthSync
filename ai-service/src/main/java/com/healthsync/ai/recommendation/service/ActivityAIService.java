package com.healthsync.ai.recommendation.service;

import com.healthsync.ai.recommendation.entity.Activity;
import com.healthsync.ai.recommendation.entity.Recommendation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public interface ActivityAIService {
    Recommendation generateRecommendation(Activity activity);
    Recommendation processAIResponse(Activity activity, String aiResponse);
    Recommendation createDefaultRecommendation(Activity activity);
    List<String> extractSafetyGuidelines(JsonNode safetyNode);
    List<String> extractSuggestions(JsonNode suggestionsNode);
    List<String> extractImprovements(JsonNode improvementsNode);
    void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix);
    String createPromptForActivity(Activity activity);
}
