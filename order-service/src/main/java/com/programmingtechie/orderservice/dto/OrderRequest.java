package com.programmingtechie.orderservice.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDtos;
}
