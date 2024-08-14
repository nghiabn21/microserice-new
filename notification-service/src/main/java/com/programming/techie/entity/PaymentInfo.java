package com.programming.techie.entity;

import com.programming.techie.dto.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payment_info")
public class PaymentInfo {

    @Id
    @SequenceGenerator(name = "PAYMENT_GENERATOR", sequenceName = "PAYMENT_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_GENERATOR")
    private Integer id;

    private String orderReference;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String customerName;
    private String customerEmail;

}
