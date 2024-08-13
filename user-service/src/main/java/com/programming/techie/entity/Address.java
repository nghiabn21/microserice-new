package com.programming.techie.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private String houseNumber;
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
