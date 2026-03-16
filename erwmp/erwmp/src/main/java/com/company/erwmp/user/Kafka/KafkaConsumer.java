package com.company.erwmp.user.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {   //Listen to Kafka topics and process incoming events.

    @KafkaListener(topics = "user-events", groupId = "group2")  //Listen to messages from Kafka topic: user-events
    public void consume(String message) {  //This method automatically runs when a message arrives.
        System.out.println("Received: " + message);
        // Process asynchronously
    }
}