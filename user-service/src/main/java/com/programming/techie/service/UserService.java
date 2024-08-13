package com.programming.techie.service;

import com.programming.techie.dto.CustomerReponse;
import com.programming.techie.utils.CustomerMapper;
import com.programming.techie.dto.CustomerRequest;
import com.programming.techie.entity.User;
import com.programming.techie.exception.CustomerNotFoundException;
import com.programming.techie.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public Integer createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(
                customerMapper.toCustomer(customerRequest));
        return customer.getId();

    }

    public void updateCustomer(CustomerRequest customerRequest) {
        User user = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new CustomerNotFoundException("ID không tìm thấy user"));
        mergeCustomer(user, customerRequest);
        customerRepository.save(user);
    }

    private void mergeCustomer(User user, CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.getName())){
            user.setName(customerRequest.getName());
        }
        if(StringUtils.isNotBlank(customerRequest.getPhone())){
            user.setPhone(customerRequest.getPhone());
        }
        if(StringUtils.isNotBlank(customerRequest.getEmail())){
            user.setEmail(customerRequest.getEmail());
        }
        if(!customerRequest.getAddress().isEmpty()){
            user.setAddress(customerRequest.getAddress());
        }
        if(StringUtils.isNotBlank(customerRequest.getDate().toString())){
            user.setDate(customerRequest.getDate());
        }
        if(StringUtils.isNotBlank(customerRequest.getStatus())){
            user.setStatus(customerRequest.getStatus());
        }

    }

    public List<CustomerReponse> findAllUser() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomerReponse)
                .collect(Collectors.toList());
    }

    public CustomerReponse findUserById(String id) {
        Integer userId = Integer.valueOf(id);
        CustomerReponse user = customerRepository.findById(userId)
                .map(customerMapper::fromCustomerReponse)
                .orElseThrow(() -> new CustomerNotFoundException("ID không tìm thấy user"));
        return user;
    }

    public Boolean existUserById(String id) {
        Integer userId = Integer.valueOf(id);
        return customerRepository.findById(userId).isPresent();
    }

    public Boolean deleteUserById(String id) {
        Integer userId = Integer.valueOf(id);
        int deleteId = customerRepository.deleteUserById(userId);
        if(deleteId > 0){
            return true ;
        }
        return false ;
    }
}
