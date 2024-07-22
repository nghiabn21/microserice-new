package com.programming.techie.config;

import com.programming.techie.service.FailureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

import java.util.List;

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
}
