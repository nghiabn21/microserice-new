package com.programmingtechie.orderservice.utils;

import com.programmingtechie.orderservice.dto.request.OrderLineRequest;
import com.programmingtechie.orderservice.dto.response.OrderLineResponse;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.getOrderId())
                .productId(request.getProductId())
                .order(
                        Order.builder()
                                .id(request.getOrderId())
                                .build()
                )
                .quantity(request.getQuantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}