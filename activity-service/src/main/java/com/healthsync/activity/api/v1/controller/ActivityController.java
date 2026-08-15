package com.healthsync.activity.api.v1.controller;

import com.healthsync.activity.api.v1.request.ActivityRequest;
import com.healthsync.activity.api.v1.response.ActivityResponse;
import com.healthsync.activity.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping("/trackactivity")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.trackActivity(request));
    }
}
