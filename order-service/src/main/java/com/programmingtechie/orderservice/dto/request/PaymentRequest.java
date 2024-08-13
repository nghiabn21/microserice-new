package com.programmingtechie.orderservice.dto.request;

import com.programmingtechie.orderservice.dto.response.CustomerResponse;
import com.programmingtechie.orderservice.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentRequest {
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Integer orderId;
    private String orderReference;
    private CustomerResponse customer;
}
