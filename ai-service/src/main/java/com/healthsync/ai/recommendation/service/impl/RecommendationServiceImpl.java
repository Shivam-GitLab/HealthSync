package com.healthsync.ai.recommendation.service.impl;

import com.healthsync.ai.recommendation.entity.Recommendation;
import com.healthsync.ai.recommendation.repository.RecommendationRepository;
import com.healthsync.ai.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    @Override
    public List<Recommendation> getUserRecommendation(String  userId) {
        return recommendationRepository.findByUserId(userId);
    }


    @Override
    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No recommendation found for this activity: " + activityId));
    }
}
