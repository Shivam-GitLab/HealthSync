package com.healthsync.ai.recommendation.service;

import com.healthsync.ai.recommendation.entity.Activity;

public interface ActivityMessageListener {
    void processActivity(Activity activity);
}
