package com.programming.techie.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {

    private Integer id;

    @NotNull(message = "Firstname is required")
    private String firstname;

    @NotNull(message = "Lastname is required")
    private String lastname;

    @NotNull(message = "Email is required")
    @Email(message = "The customer email is not correctly formatted")
    private String email;
}
