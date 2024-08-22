package com.programming.techie.config;

import com.programming.techie.service.FailureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@EnableKafka
public class KafkaConfig {

    @Autowired
    FailureService failureService;

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory);
        // thử lại bản ghi không thành công 2 lần độ trễ 1s
        factory.setCommonErrorHandler(errorHandler());
        return factory;

    }

    private DefaultErrorHandler errorHandler() {

        var exceptiopnToRetryIgnorelist = List.of(
                RecoverableDataAccessException.class
        );

        ExponentialBackOffWithMaxRetries expBackOff = new ExponentialBackOffWithMaxRetries(2);
        expBackOff.setInitialInterval(1_000L); // lần gọi đầu là 1s
        expBackOff.setMultiplier(2.0);    // các lần gọi từ lần t2 sẽ là 2s ???
        expBackOff.setMaxInterval(2_000L);

        var errorHandle = new DefaultErrorHandler(consumerRecordRecoverer, expBackOff);

        errorHandle.setRetryListeners(
                (record, ex, deliveryAttempt) ->
                        log.info("Failed Record in Retry Listener  exception : {} , deliveryAttempt : {} "
                                , ex.getMessage(), deliveryAttempt)
        );

        exceptiopnToRetryIgnorelist.forEach(errorHandle::addNotRetryableExceptions);
        return errorHandle;
    }

    ConsumerRecordRecoverer consumerRecordRecoverer = (record, exception) -> {
        log.error("Exception is : {} Failed Record : {} ", exception, record);
        if (exception.getCause() instanceof RecoverableDataAccessException) {
            log.info("Inside the recoverable logic");
            //Add any Recovery Code here.
            failureService.saveFailedRecord((ConsumerRecord<Integer, String>) record, exception);

        } else {
            // non-recovery-logic
            log.info("Inside the non recoverable logic and skipping the record : {}", record);
//            failureService.saveFailedRecord((ConsumerRecord<Integer, String>) record, exception, DEAD);
        }
    };


//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "request-reply-group");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(configProps);
//    }
//
//    @Bean
//    public ConcurrentMessageListenerContainer<String, String> messageListenerContainer() {
//        ContainerProperties containerProps = new ContainerProperties(KafkaHeaders.TOPIC);
//        return new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);
//    }
}
