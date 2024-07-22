package com.programmingtechie.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        orderService.placeOrder(orderRequest);
        log.info("Sau khi truy van xong");
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Successfully");
//        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    /**
     * CompletableFuture: lập trình bất đồng bộ và xử lý đồng thời.
     * Nó cho phép bạn tạo ra các tác vụ (tasks) và xử lý các kết quả của chúng khi chúng hoàn thành.
     * supplyAsync: khởi tạo và thực thi một tác vụ bất đồng bộ (asynchronous task) và trả về một kết quả
     * khi chạy gọi service: sẽ được gọi và thực thi trong một luồng (thread) khác, không chặn luồng chính của ứng dụng.
     */
    @PostMapping("/fall-back")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> placeOrderFallBack(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        orderService.placeOrder(orderRequest);
        log.info("Called to kafka and this is controller");
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Successfully");
//        return  orderService.placeOrder(orderRequest);
    }


    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }
}
