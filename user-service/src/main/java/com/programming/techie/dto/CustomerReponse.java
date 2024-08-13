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
public class CustomerReponse {

    private Integer id ;
    private String name ;
    private String email ;
    private List<Address> address;
    private String phone;
    private LocalDateTime date;
    private String status;
}
