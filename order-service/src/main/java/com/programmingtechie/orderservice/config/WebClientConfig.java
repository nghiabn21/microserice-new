package com.programmingtechie.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // khi 1 service chạy trên nhiều cổng mà khi 1 ser A gọi tới ser B ( chạy 3 cổng) thì nó sẽ chia chạy đều trên 3 cổng
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
