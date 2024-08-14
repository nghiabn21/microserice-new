package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.request.OrderLineRequest;
import com.programmingtechie.orderservice.dto.response.OrderLineResponse;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLine;
import com.programmingtechie.orderservice.repository.OrderLineRepository;
import com.programmingtechie.orderservice.utils.OrderLineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        OrderLine order = OrderLine.builder()
                .id(request.getOrderId())
                .productId(request.getProductId())
                .orders(
                        Order.builder()
                                .id(request.getOrderId())
                                .build()
                )
                .quantity(request.getQuantity())
                .build();
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(this::toOrderLineResponse)
                .collect(Collectors.toList());
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
