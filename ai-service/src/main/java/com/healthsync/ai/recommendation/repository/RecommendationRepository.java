package com.healthsync.ai.recommendation.repository;

import com.healthsync.ai.recommendation.entity.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    List<Recommendation> findByUserId(Long userId);

    Optional<Recommendation> findByActivityId(String activityId);
}
