package com.healthsync.activity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
public record KafkaTopicProperties(String name) {
}