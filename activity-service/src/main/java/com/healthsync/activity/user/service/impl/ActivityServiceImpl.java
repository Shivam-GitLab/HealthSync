package com.healthsync.activity.user.service.impl;

import com.healthsync.activity.api.v1.request.ActivityRequest;
import com.healthsync.activity.api.v1.response.ActivityResponse;
import com.healthsync.activity.user.entity.Activity;
import com.healthsync.activity.user.mapper.ActivityMapper;
import com.healthsync.activity.user.repository.ActivityRepository;
import com.healthsync.activity.user.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {
        final Activity activity = activityMapper.toEntity(request);
        final Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toResponse(savedActivity);
    }
}
