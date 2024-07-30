package com.programmingtechie.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // liên kết bảng user -> ai đặt hàng
    private Integer customerId;
    // mã đơn hàng
    private String orderNumber;
    // tổnsosotieenf trả
    private Integer totalPayment;

    // ngàytaojo đơn
    private LocalDateTime createDate;

    // ngày du tinh ship
    private LocalDateTime dateShip;

    // dia chi ship
    private String addressShip;

    // trang thai than toán
    private String statusPayment;

    // ai ship
    private Integer shipperId;

    // nhieuf món hàng
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetail> orderLineItemsList;
}
