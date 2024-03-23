package com.rmk.kafka.producer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Configuration
@Log4j2
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.prod.topic:}")
    private String topic; // Replace with your desired topic name

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Map sendMessage(String key, Object message) {
        try {
            var result = kafkaTemplate.send(topic, key, convertObjectToJSON(message));
            RecordMetadata metaData = result.get().getRecordMetadata();
            var res = Map.of("offset", metaData.offset(), "timestamp", metaData.timestamp(), "partition", metaData.partition(), "topic", metaData.topic());
            System.out.println(res);
            return res;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error: {}", e);
        }

        return Map.of();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return mapper;
    }

    private String convertObjectToJSON(Object object) {
        try {
            var objectMapper = objectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error: {}", e);
            return "{}";
        }
    }
}
