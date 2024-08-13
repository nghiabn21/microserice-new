package com.programmingtechie.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
}
