package com.programmingtechie.orderservice.dto.request;


import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.model.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDtos;

    private Integer id;
    private String reference;

    @Positive(message = "Order amount should be positive")
    private BigDecimal amount;

    @NotNull(message = "Phương thức thanh toán phải được chính xác")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Customer should be present")  // không phải null
//    @NotEmpty(message = "Customer should be present") // không null và không phải chuối hay list rỗng ""
//    @NotBlank(message = "Customer should be present") // không null, không chuỗi hay list rỗng "", không chứa khoảng trắng
    private Integer customerId;

    @NotEmpty(message = "Bạn nên mua ít nhất một sản phẩm")
    private List<PurchaseRequest> products;
}
