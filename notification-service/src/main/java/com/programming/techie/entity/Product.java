package com.programming.techie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "order_info_id")
    private OrderInfo orderInfo;
}
