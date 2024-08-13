package com.programming.techie.service;

import com.programming.techie.dto.PaymentNotificationRequest;
import com.programming.techie.dto.PaymentRequest;
import com.programming.techie.notification.NotificationProducer;
import com.programming.techie.repository.PaymentRepository;
import com.programming.techie.utils.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = this.repository.save(this.mapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.getOrderReference(),
                        request.getAmount(),
                        request.getPaymentMethod(),
                        request.getCustomer().getFirstname(),
                        request.getCustomer().getLastname(),
                        request.getCustomer().getEmail()
                )
        );
        return payment.getId();
    }
}
