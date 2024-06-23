package com.demo.deliverymanservice.config.broker;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("kafka")
public class KafkaConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private final Map<String, String> topicNameMap = new HashMap<>();
    private final GenericApplicationContext context;

    public KafkaConfig(GenericApplicationContext context) {
        this.context = context;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> adminConfigs = new HashMap<>();
        adminConfigs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(adminConfigs);
    }

    @PostConstruct
    public void registerTopics() {
        for (var entry : topicNameMap.entrySet()) {
            this.context.registerBean(entry.getKey(), NewTopic.class, () -> new NewTopic(entry.getValue(), 1, (short) 1));
        }
    }
}
