package com.healthsync.activity.activity.service;

import com.healthsync.activity.api.v1.request.ActivityRequest;
import com.healthsync.activity.api.v1.response.ActivityResponse;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);

//    List<ActivityResponse> getUserActivities(String userId);
}
