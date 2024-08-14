package com.programming.techie.entity;

import com.programming.techie.dto.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private PaymentMethod paymentMethod;
    private String customerName;
    private String customerEmail;

}
