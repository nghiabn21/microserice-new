package com.programming.techie.entity;

import com.programming.techie.dto.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_info")
public class OrderInfo {

    @Id
    @SequenceGenerator(name = "ORDER_GENERATOR", sequenceName = "ORDER_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_GENERATOR")
    private Integer id;

    private String orderReference;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderInfo")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderInfo")
    private List<Product> products;
}
