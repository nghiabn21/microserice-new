package com.programmingtechie.orderservice.model;


import lombok.*;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // liên kết bảng user -> ai đặt hàng
    private Integer customerId;

    private String reference;
    // mã đơn hàng
    private String orderNumber;
    // tổng tiền trả
    private BigDecimal totalAmount;

    // ngày tạo đơn
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    // ngày du tinh ship
//    private LocalDateTime dateShip;

    // dia chi ship
//    private String addressShip;

    // trang thai than toán
//    private String statusPayment;

    // ai ship
//    private Integer shipperId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // nhieuf món hàng
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderLine> orderLines;
}
