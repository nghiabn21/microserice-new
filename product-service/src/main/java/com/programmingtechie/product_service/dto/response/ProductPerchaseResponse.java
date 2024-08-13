package com.programmingtechie.product_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPerchaseResponse {

    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private double quantity;
}
