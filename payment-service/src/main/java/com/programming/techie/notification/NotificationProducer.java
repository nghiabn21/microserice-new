package com.programming.techie.notification;

import com.programming.techie.dto.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequest request) {
        log.info("Sending notification with body = < {} >", request);
        Message<PaymentNotificationRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, "payment-topic")
                .build();

//        CompletableFuture<SendResult<String, PaymentNotificationRequest>> future = kafkaTemplate.send(message);
//
//        return future.whenComplete((sendResult, throwable) -> {
//            if (throwable != null) {
//                log.error("Error Sending the Message and the exception is {}", throwable.getMessage());
//            } else {
//                log.info("Message Sent SuccessFully for partition is {}",sendResult.getRecordMetadata().partition());
//            }
//        });

        kafkaTemplate.send(message);
    }
}
