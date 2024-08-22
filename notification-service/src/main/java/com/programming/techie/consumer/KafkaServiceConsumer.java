package com.programming.techie.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.util.concurrent.ListenableFuture;
import com.programming.techie.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaServiceConsumer {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(ConsumerRecord<Integer, String> orderPlacedEvent) throws JsonProcessingException {
        //send out an email notification
        log.info("Received notification for order - {}", orderPlacedEvent);
        kafkaService.processSendTopic(orderPlacedEvent);
    }

    @KafkaListener(topics = KafkaHeaders.TOPIC)
    public void test(ConsumerRecord<String, String> orderPlacedEvent) throws JsonProcessingException {
        //send out an email notification
        log.info("Received notification for order - {}", orderPlacedEvent.value());

        Header replyTopicHeader = orderPlacedEvent.headers().lastHeader(KafkaHeaders.REPLY_TOPIC);
        if (replyTopicHeader == null) {
            log.error("Header with key " + orderPlacedEvent.topic() + " not found, no headers");
        }
        String replyTopic = new String(replyTopicHeader.value());

        // Send response message to reply topic
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(
                new ProducerRecord<>(replyTopic, "1")
        );

        future.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                log.error("Error Sending the Message and the exception is {}", throwable.getMessage());
            } else {
                log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                        sendResult.getRecordMetadata().toString());
            }
        });
//        kafkaService.processSendTopic(orderPlacedEvent);
    }
}
