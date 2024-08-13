package com.programmingtechie.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseResponse {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private double quantity;
}
