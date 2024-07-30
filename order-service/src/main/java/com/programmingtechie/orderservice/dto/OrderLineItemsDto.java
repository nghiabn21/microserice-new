package com.programmingtechie.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderLineItemsDto {

    private Long id;
    // san pham nao dc dat
    private Integer productId;

    private String skuCode;

    // giá
    private BigDecimal price;

    // so luong muon dat
    private Integer quantity;
}
