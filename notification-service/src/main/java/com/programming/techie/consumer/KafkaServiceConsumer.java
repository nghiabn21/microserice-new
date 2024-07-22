package com.programming.techie.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.programming.techie.entity.OrderPlacedEvent;
import com.programming.techie.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaServiceConsumer {

    @Autowired
    private KafkaService kafkaService;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(ConsumerRecord<Integer, String> orderPlacedEvent) throws JsonProcessingException {
        //send out an email notification
        log.info("Received notification for order - {}", orderPlacedEvent);
        kafkaService.processSendTopic(orderPlacedEvent);
    }
}
