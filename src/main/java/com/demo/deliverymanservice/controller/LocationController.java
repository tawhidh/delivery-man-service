package com.demo.deliverymanservice.controller;

import com.demo.deliverymanservice.service.KafkaEventSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final KafkaEventSender kafkaEventSender;
    public LocationController(KafkaEventSender kafkaEventSender) {
        this.kafkaEventSender = kafkaEventSender;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation() {
        kafkaEventSender.updateLocation("( " + Math.round(Math.random() * 100) + " , " + Math.round(Math.random() * 100) + " )");
        return new ResponseEntity<>(Map.of("message", "location updated"), HttpStatus.OK);
    }
}