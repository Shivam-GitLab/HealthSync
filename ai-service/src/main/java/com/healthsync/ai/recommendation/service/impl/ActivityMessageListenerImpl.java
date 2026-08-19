package com.healthsync.ai.recommendation.service.impl;

import com.healthsync.ai.recommendation.entity.Activity;
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
    @Override
    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "activity-processor-group"
    )
    public void processActivity(Activity activity) {

        log.info("Received Activity for processing: {}", activity.getUserId());

        try {
            activityAIService.generateRecommendation(activity);
        } catch (Exception e) {
            log.error(
                    "Failed to generate recommendation for userId={}: {}",
                    activity.getUserId(),
                    e.getMessage()
            );
        }
    }
}
