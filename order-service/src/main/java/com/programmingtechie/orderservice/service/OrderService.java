package com.programmingtechie.orderservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.event.OrderPlacedEvent;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    @Value("${spring.kafka.topic}")
    public String topic;

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClient;

    private final Tracer tracer;

    private final KafkaTemplate<Integer, String> kafkaTemplate;


    public CompletableFuture<SendResult<Integer, String>> placeOrder(OrderRequest orderRequest) throws JsonProcessingException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderLineItems = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(order1 -> mapToDto(order1))
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> listSkuCode = order.getOrderLineItemsList().stream()
                .map(OrderItems::getSkuCode)
                .toList();

        InventoryResponse[] result = webClient.build()
                .get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", listSkuCode).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); // block: tạo yêu cầu đồng bộ

        boolean allProductInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

        // nếu tất cả sản phẩm có trong kho thì lưu cái order vào
        if (allProductInStock) {
            orderRepository.save(order);
            log.info("Started send message to kafka");
            Integer key = order.getId();
            String orderListItemId = order.getOrderLineItemsList().get(0).getSkuCode();

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setOrderListItemId(orderListItemId);

            String value = objectMapper.writeValueAsString(orderPlacedEvent);
            log.info("value: {}", value);
            ProducerRecord<Integer, String> producerRecord = buildProducerRecord(topic, key, value);
            // xử lý k đồng bộ
            // mặc dù log ở controller là sau nhưng nó sẽ k chờ gọi pass hay failed nó vẫn chạy controller trc
            // gửi fail hay success thì nó sẽ in mess sau đó
            CompletableFuture<SendResult<Integer, String>> completableFuture = kafkaTemplate.send(producerRecord);

            return  completableFuture.whenComplete((sendResult, throwable) -> {
                  if(throwable != null){
                      log.error("Error Sending the Message and the exception is {}", throwable.getMessage());
                  }else {
                      log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                              key, value, sendResult.getRecordMetadata().partition());
                  }
            });
//            kafkaTemplate.send(topic, new OrderPlacedEvent(order.getOrderNumber()));
//            return "Order Successfully";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

    private ProducerRecord<Integer, String> buildProducerRecord(String topic, Integer key, String value) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }

    private OrderItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderItems orderLineItems = new OrderItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

//    public String placeOrderDisplayZipkin(OrderRequest orderRequest) {
//        Order order = new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//
//        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos()
//                .stream()
//                .map(order1 -> mapToDto(order1))
//                .toList();
//
//        order.setOrderLineItemsList(orderLineItems);
//
//        List<String> listSkuCode = order.getOrderLineItemsList().stream()
//                .map(OrderLineItems::getSkuCode)
//                .toList();
//
//        //Code test display in zipkin
//        Span span = tracer.nextSpan().name("InventoryServiceLookUp: ");
//        try (Tracer.SpanInScope spanInScope = tracer.withSpan(span.start())) {
//            // Call Inventory Service, and place order if product is in
//            // stock
//            InventoryResponse[] result = webClient.build()
//                    .get()
//                    .uri("http://inventory-service/api/inventory",
//                            uriBuilder -> uriBuilder.queryParam("skuCode", listSkuCode).build())
//                    .retrieve()
//                    .bodyToMono(InventoryResponse[].class)
//                    .block(); // block: tạo yêu cầu đồng bộ
//
//            boolean allProductInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);
//
//            // nếu tất cả sản phẩm có trong kho thì lưu cái order vào
//            if (allProductInStock) {
//                orderRepository.save(order);
//
////                kafkaTemplate.send(topic, new OrderPlacedEvent(order.getOrderNumber()));
//
//                return "Order Successfully";
//            } else {
//                throw new IllegalArgumentException("Product is not in stock, please try again later");
//            }
//        } finally {
//            span.end();
//        }
//
//    }
}
