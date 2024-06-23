package com.demo.deliverymanservice.service;

import com.demo.deliverymanservice.config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaEventSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void updateLocation(String event) {
        this.kafkaTemplate.send(Constant.TOPIC_LOCATION_UPDATE, event);
        log.info("location produced: " + event);
    }
}
