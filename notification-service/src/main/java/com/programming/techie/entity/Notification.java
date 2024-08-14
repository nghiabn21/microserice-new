package com.programming.techie.entity;


import com.programming.techie.Enum.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private NotificationType type;
    private LocalDateTime notificationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderInfo orderConfirmation;

    @OneToOne(cascade = CascadeType.ALL)
    private PaymentInfo paymentConfirmation;
}
