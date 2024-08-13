package com.programming.techie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name ;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status;

    // nếu không có mappedBy sẽ sinh ra 1 bảng ở giữa như branch_product_list
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Address> address;

}
