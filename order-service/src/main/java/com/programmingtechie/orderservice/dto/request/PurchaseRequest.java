package com.programmingtechie.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Validated
public class PurchaseRequest {
    @NotNull(message = "Sản phẩm là bắt buộc")
    private Integer productId;

    @Positive(message = "Số lượng là bắt buộc")
    private double quantity;
}
