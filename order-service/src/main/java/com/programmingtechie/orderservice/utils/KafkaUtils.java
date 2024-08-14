package com.programmingtechie.orderservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.internals.Topic;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;


@Log4j2
public class KafkaUtils {
    public static Integer getMessageKafka(ReplyingKafkaTemplate<String, Object, Object> replyingTemplate, String key, String a) {
        try {
            log.info("getMessageKafka : request send kafka -----> ");
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(a);;

            ProducerRecord<String, Object> record = buildProducerRecord(TOPIC,key,value);

            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, a.getBytes()));
            record.headers().add(new RecordHeader(KafkaHeaders.CORRELATION_ID, UUID.randomUUID().toString().getBytes()));

            RequestReplyFuture<String, Object, Object> replyFuture = replyingTemplate.sendAndReceive(record);

            ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);

            String val = consumerRecord.value().toString();

            log.info("getMessageKafka: read message consumer success by refNo ------> {}", val);

            Integer id = Integer.parseInt(consumerRecord.headers().lastHeader(TOPIC).value().toString());

            return id;
        }catch (TimeoutException e) {
            log.error("getMessageKafka: getMessageKafka timeout -----> {} by refNo --->{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            log.error("getMessageKafka: getMessageKafka error -----> {} by refNo --->{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static ProducerRecord<String, Object> buildProducerRecord(String topic, String key, String value) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }
}