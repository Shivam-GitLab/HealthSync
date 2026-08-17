package com.healthsync.ai.recommendation.service.impl;

import com.healthsync.ai.recommendation.entity.Activity;
import com.healthsync.ai.recommendation.service.ActivityMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity) {
        log.info("Received Activity for processing: {}", activity.getUserId());
    }
}
