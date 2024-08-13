package com.programmingtechie.orderservice.model;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_line_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // id san pham nao dc dat
    private Integer productId;

    // so luong muon dat
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orders;

}
