package com.healthsync.activity.activity.service.impl;

import com.healthsync.activity.api.v1.request.ActivityRequest;
import com.healthsync.activity.api.v1.response.ActivityResponse;
import com.healthsync.activity.activity.entity.Activity;
import com.healthsync.activity.activity.mapper.ActivityMapper;
import com.healthsync.activity.activity.repository.ActivityRepository;
import com.healthsync.activity.activity.service.ActivityService;
import com.healthsync.activity.config.KafkaTopicProperties;
import com.healthsync.activity.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicPropertiesTopic;



    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {
        Boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if (!isValidUser) {
            throw new RuntimeException("INVALID USER : " + request.getUserId());
        }
        final Activity activity = activityMapper.toEntity(request);
        final Activity savedActivity = activityRepository.save(activity);
        try {
            kafkaTemplate.send(
                    kafkaTopicPropertiesTopic.name(),
                    savedActivity.getUserId(),
                    savedActivity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return activityMapper.toResponse(savedActivity);
    }
}
