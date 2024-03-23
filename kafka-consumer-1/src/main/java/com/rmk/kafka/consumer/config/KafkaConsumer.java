package com.rmk.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.consumer.topic:}", groupId = "${spring.kafka.consumer.group-id:}")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        //System.out.println(record.key());
        System.out.println(record.value());
        //System.out.println(record.partition());
        //System.out.println(record.topic());
        //System.out.println(record.offset());
    }
}
