package com.healthsync.ai.recommendation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.healthsync.ai.recommendation.entity.Activity;
import com.healthsync.ai.recommendation.entity.Recommendation;
import com.healthsync.ai.recommendation.service.ActivityAIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityAIServiceImpl implements ActivityAIService {

    private final GeminiAIServiceImpl geminiAIServiceImpl;

    @Override
    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiAIServiceImpl.getRecommendations(prompt);
        log.info("RESPONSE FROM AI {} ", aiResponse);
        return processAIResponse(activity, aiResponse);
    }

    @Override
    public Recommendation processAIResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```","")
                    .trim();

//            log.info("RESPONSE FROM CLEANED AI {} ", jsonContent);

            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories:");

            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("Failed to process Gemini AI request", e);
            return createDefaultRecommendation(activity);
        }
    }

    @Override
    public Recommendation createDefaultRecommendation(Activity activity) {
        return null;
    }

    @Override
    public List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        return List.of();
    }

    @Override
    public List<String> extractSuggestions(JsonNode suggestionsNode) {
        return List.of();
    }

    @Override
    public List<String> extractImprovements(JsonNode improvementsNode) {
        return List.of();
    }

    @Override
    public void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {

    }

    @Override
    public String createPromptForActivity(Activity activity) {
        return "";
    }
}
