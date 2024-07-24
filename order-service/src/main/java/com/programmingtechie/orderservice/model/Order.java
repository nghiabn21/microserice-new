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

    private Integer customerId;

    private String orderNumber;

    private Integer totalPayment;

    private LocalDateTime createDate;

    private LocalDateTime dateShip;

    private String addressShip;

    private String statusPayment;

    private Integer shipperId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetail> orderLineItemsList;
}
