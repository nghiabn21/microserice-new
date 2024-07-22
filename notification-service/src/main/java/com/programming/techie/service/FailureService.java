package com.programming.techie.service;


import com.programming.techie.entity.FailureRecord;
import com.programming.techie.repo.FailureRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FailureService {

    @Autowired
    private FailureRepository failureRecordRepository;

    public static final String STATUS_ERROR = "retry";

    public void saveFailedRecord(ConsumerRecord<Integer, String> record, Exception exception) {
        log.info("Into method save when try send message error");
        FailureRecord failureRecord = FailureRecord.builder().
                topic(record.topic()).
                keyValue(record.key()).
                errorRecord(record.value()).
                partition(record.partition()).
                offsetValue(record.offset()).
                exception(exception.getCause().getMessage()).
                status(STATUS_ERROR).
                build();

        failureRecordRepository.save(failureRecord);

    }
}
