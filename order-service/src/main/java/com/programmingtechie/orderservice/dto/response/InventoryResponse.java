package com.programmingtechie.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

    // mã code của sản phẩm
    private String skuCode;

    // kiểm tra còn hàng trong kho hay k
    private boolean isInStock;
}
