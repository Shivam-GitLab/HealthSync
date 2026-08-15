package com.healthsync.activity.activity.repository;

import com.healthsync.activity.activity.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository
        extends MongoRepository<Activity, String> {
    List<Activity> findByUserId(String userId);
}