package com.programmingtechie.orderservice.dto;

import com.programmingtechie.orderservice.dto.response.CustomerResponse;
import com.programmingtechie.orderservice.dto.response.PurchaseResponse;
import com.programmingtechie.orderservice.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderConfirmation {
    private String orderReference;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private CustomerResponse customer;
    private List<PurchaseResponse> products;
}
