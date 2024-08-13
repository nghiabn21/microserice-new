package com.programmingtechie.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderResponse {

    private Integer id;
    private String reference;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Integer customerId;
}
