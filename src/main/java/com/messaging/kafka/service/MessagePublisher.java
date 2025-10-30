package com.messaging.kafka.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class MessagePublisher {

    KafkaTemplate<String, Object> template;

    public void publishToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("kafka-passthrough", message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message = [{}] whit offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message = [{}] due to : {}", message, ex.getMessage());
            }
        });

    }

}
