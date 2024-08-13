package com.programmingtechie.orderservice.utils;

import com.programmingtechie.orderservice.dto.request.OrderRequest;
import com.programmingtechie.orderservice.dto.response.OrderResponse;
import com.programmingtechie.orderservice.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        if (request == null) {
            return null;
        }
        return Order.builder()
                .id(request.getId())
                .reference(request.getReference())
                .paymentMethod(request.getPaymentMethod())
                .customerId(request.getCustomerId())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
