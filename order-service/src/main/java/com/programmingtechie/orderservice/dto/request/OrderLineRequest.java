package com.programmingtechie.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderLineRequest {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private double quantity;
}
