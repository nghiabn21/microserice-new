package com.programming.techie.consumer;


import com.programming.techie.email.EmailService;
import com.programming.techie.dto.OrderConfirmation;
import com.programming.techie.dto.PaymentConfirmation;
import com.programming.techie.entity.Notification;
import com.programming.techie.entity.OrderInfo;
import com.programming.techie.entity.PaymentInfo;
import com.programming.techie.repo.NotificationRepository;
import com.programming.techie.entity.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        String sequenceName = "PAYMENT_SEQ";
        int id = jdbcTemplate.queryForObject("SELECT " + sequenceName + ".nextval FROM dual", Integer.class);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .id(id)
                .orderReference(paymentConfirmation.getOrderReference())
                .amount(paymentConfirmation.getAmount())
                .paymentMethod(paymentConfirmation.getPaymentMethod())
                .customerName(paymentConfirmation.getCustomerName())
                .customerEmail(paymentConfirmation.getCustomerEmail())
                .build();

        repository.save( Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentInfo)
                        .build()
        );
        String customerName = paymentConfirmation.getCustomerName();

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.getCustomerEmail(),
                customerName,
                paymentConfirmation.getAmount(),
                paymentConfirmation.getOrderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));

        String sequenceName = "ORDER_SEQ";
        int id = jdbcTemplate.queryForObject("SELECT " + sequenceName + ".nextval FROM dual", Integer.class);

        OrderInfo orderInfo = OrderInfo.builder()
                .id(id)
                .orderReference(orderConfirmation.getOrderReference())
                .totalAmount(orderConfirmation.getTotalAmount())
                .paymentMethod(orderConfirmation.getPaymentMethod())
                .customer(orderConfirmation.getCustomer())
                .build();

        repository.save( Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderInfo)
                        .build()
        );
        String customerName = orderConfirmation.getCustomer().getName();

        emailService.sendOrderConfirmationEmail(
                orderConfirmation.getCustomer().getEmail(),
                customerName,
                orderConfirmation.getTotalAmount(),
                orderConfirmation.getOrderReference(),
                orderConfirmation.getProducts()
        );
    }
}