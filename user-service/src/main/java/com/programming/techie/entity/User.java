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

    @Column(name = "status",columnDefinition = "varchar(255) default 'TRUE'")
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address;

}
