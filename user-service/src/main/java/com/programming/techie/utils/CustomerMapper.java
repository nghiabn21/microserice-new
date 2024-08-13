package com.programming.techie.utils;

import com.programming.techie.dto.CustomerReponse;
import com.programming.techie.dto.CustomerRequest;
import com.programming.techie.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public User toCustomer(CustomerRequest customerRequest) {
        if(customerRequest == null){
            throw new RuntimeException("Lỗi không có request") ;
        }
       return User.builder()
               .name(customerRequest.getName())
               .email(customerRequest.getEmail())
               .phone(customerRequest.getPhone())
               .address(customerRequest.getAddress())
               .date(customerRequest.getDate())
               .status(customerRequest.getStatus())
               .build();
    }

    public CustomerReponse fromCustomerReponse(User user) {
        return CustomerReponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .date(user.getDate())
                .status(user.getStatus())
                .build();
    }
}
