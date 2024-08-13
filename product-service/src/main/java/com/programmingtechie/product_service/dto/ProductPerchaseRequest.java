package com.programmingtechie.product_service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPerchaseRequest {
    @NotNull(message = "Product is mandatory")
    private Integer productId;
    @NotNull(message = "quantity is mandatory")
    private double quantity;
}
