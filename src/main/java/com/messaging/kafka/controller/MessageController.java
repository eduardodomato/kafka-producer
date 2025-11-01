package com.messaging.kafka.controller;

import com.messaging.kafka.dto.Customer;
import com.messaging.kafka.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessagePublisher messagePublisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage (@PathVariable("message") String message){
        try {
            messagePublisher.publishToTopic(message);
            return ResponseEntity.ok("Message published.");
        }catch (Exception e){
            log.error("Exception when publishing message: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/publish/customer")
    public ResponseEntity<?> sendEvents (@RequestBody Customer customer){
      try {
        messagePublisher.publishToTopic(customer);
        return ResponseEntity.ok("Customer published.");
      } catch (Exception e) {
        log.error("Exception when publishing customer: {}", e.getMessage());
        return ResponseEntity.internalServerError().build();
      }
    }



}
