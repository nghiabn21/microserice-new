package com.programming.techie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programming.techie.dto.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private ObjectMapper objectMapper;

    public void processSendTopic(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        OrderPlacedEvent orderPlacedEvent = objectMapper.readValue(consumerRecord.value(), OrderPlacedEvent.class);
        log.info("Run in kafkaService: {}", orderPlacedEvent);
        if(orderPlacedEvent.getOrderNumber() == null){
            throw new RecoverableDataAccessException("Lỗi ở service!!!");
        }
        log.info("Successfully");
    }
}
