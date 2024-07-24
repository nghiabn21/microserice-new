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

    private String orderNumber;

    private Integer totalPayment;

    private LocalDateTime orderDate;

    private LocalDateTime shipDate;

    private String shipAddress;

    private String statusPayment;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItems> orderLineItemsList;
}
