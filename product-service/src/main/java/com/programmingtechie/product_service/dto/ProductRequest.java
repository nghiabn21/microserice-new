package com.programmingtechie.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Integer id;
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    @Positive(message = "price is positive")
    private BigDecimal price;

    @NotNull(message = "availableQuantity is required")
    @Positive(message = "availableQuantity is positive")
    private double availableQuantity;

    @NotNull(message = "brandId is required")
    private Integer brandId;
}
