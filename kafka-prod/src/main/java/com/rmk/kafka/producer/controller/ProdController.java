package com.rmk.kafka.producer.controller;

import com.rmk.kafka.producer.config.KafkaProducer;
import com.rmk.kafka.producer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProdController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/user")
    public ResponseEntity sendUser(@RequestBody User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        return ResponseEntity.ok(kafkaProducer.sendMessage(uuid, user));
    }
}
