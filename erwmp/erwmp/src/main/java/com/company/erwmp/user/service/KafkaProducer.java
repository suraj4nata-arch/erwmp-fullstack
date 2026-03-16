package com.company.erwmp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer  //basically this class is to send the messages
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; //provided by spring kafka, Send messages to Kafka

    private static final String TOPIC = "user-events"; //stores the message inside a topic

    public void sendMessage(String message) { //message → KafkaTemplate → Kafka Broker → Topic
        System.out.println("Publishing message: " + message);
        kafkaTemplate.send(TOPIC, message);
    }
}
