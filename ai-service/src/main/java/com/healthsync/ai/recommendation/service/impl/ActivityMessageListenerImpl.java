package com.healthsync.ai.recommendation.service.impl;

import com.healthsync.ai.recommendation.entity.Activity;
import com.healthsync.ai.recommendation.entity.Recommendation;
import com.healthsync.ai.recommendation.repository.RecommendationRepository;
import com.healthsync.ai.recommendation.service.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    private final ActivityAIServiceImpl activityAIService;
    private final RecommendationRepository recommendationRepository;
    @Override
    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "activity-processor-group"
    )
    public void processActivity(Activity activity) {

        log.info("Received Activity for processing: {}", activity.getUserId());

        try {
            Recommendation recommendation = activityAIService.generateRecommendation(activity);
            recommendationRepository.save(recommendation);
            log.info("Successfully Saved Data: {}", activity.getUserId());
        } catch (Exception e) {
            log.error(
                    "Failed to generate recommendation for userId={}: {}",
                    activity.getUserId(),
                    e.getMessage()
            );
        }
    }
}
