package com.programming.techie.dto;


import com.programming.techie.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    private Integer id ;

    @NotNull(message = "Customer name is required")
    private String name ;

    @NotNull(message = "Customer name is required")
    @Email(message = "Email iss not valid")
    private String email ;

    @NotNull(message = "Customer name is required")
    private List<Address> address;

    private String phone;
    private LocalDateTime date;
    private String status;
}
