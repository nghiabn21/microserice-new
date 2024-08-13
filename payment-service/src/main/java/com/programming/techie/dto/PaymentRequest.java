package com.programming.techie.dto;

import com.programming.techie.entity.Customer;
import com.programming.techie.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentRequest {
    private Integer id;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Integer orderId;
    private String orderReference;
    private Customer customer;
}
