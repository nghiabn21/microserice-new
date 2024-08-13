package com.programmingtechie.product_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    // nếu không có mappedBy sẽ sinh ra 1 bảng ở giữa như branch_product_list
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Product> productList;
}
