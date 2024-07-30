package com.programmingtechie.orderservice.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDtos;
}
