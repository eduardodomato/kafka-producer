package com.messaging.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class KafkaProducerConfig {

    @Bean
    @Lazy
    @ConditionalOnProperty(name = "kafka.topic.create.enabled", havingValue = "true", matchIfMissing = false)
    public NewTopic createTopic(){
        return new NewTopic("kafka-alternate-topic",2,(short) 1);
    }

}
