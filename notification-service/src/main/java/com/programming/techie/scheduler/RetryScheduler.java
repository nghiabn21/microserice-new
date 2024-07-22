package com.programming.techie.scheduler;


import com.programming.techie.entity.FailureRecord;
import com.programming.techie.repo.FailureRepository;
import com.programming.techie.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RetryScheduler {

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private KafkaService kafkaService;

    public static final String STATUS_ERROR = "retry";

    @Scheduled(fixedRate = 10000)
    public void retryFailedRecords() {
        log.info("Run in retry scheduler with message error");
        List<FailureRecord> failureRecordList = failureRepository.findByStatus(STATUS_ERROR);
        failureRecordList.stream().forEach(failureRecord -> {
            try {
                log.info("Message lỗi!: {}", failureRecord);
                ConsumerRecord<Integer, String> consumerRecord = buildConsumerRecord(failureRecord);
                kafkaService.processSendTopic(consumerRecord);
                failureRecord.setStatus("SUCCESS");
                failureRepository.save(failureRecord);
            } catch (Exception e) {
                log.error("Gọi lại không thành công !!! Exception in retry FailedRecords : ", e);
            }
        });
    }

    private ConsumerRecord<Integer, String> buildConsumerRecord(FailureRecord failureRecord) {
        return new ConsumerRecord<>(failureRecord.getTopic(),
                failureRecord.getPartition(),
                failureRecord.getOffsetValue(),
                failureRecord.getKeyValue(),
                failureRecord.getErrorRecord());
    }
}
