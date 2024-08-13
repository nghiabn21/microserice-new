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
        User user = User.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .address(customerRequest.getAddress())
                .date(customerRequest.getDate())
                .status(customerRequest.getStatus())
                .build();
        User customer = customerRepository.save(user);
        return customer.getId();

    }

    public User updateCustomer(CustomerRequest customerRequest) {
        User user = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new CustomerNotFoundException("ID không tìm thấy user"));
        mergeCustomer(user, customerRequest);
        User user1 = customerRepository.save(user);
        return user1;
    }

    private void mergeCustomer(User user, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.getName())) {
            user.setName(customerRequest.getName());
        }
        if (StringUtils.isNotBlank(customerRequest.getPhone())) {
            user.setPhone(customerRequest.getPhone());
        }
        if (StringUtils.isNotBlank(customerRequest.getEmail())) {
            user.setEmail(customerRequest.getEmail());
        }
        if (!customerRequest.getAddress().isEmpty()) {
            user.setAddress(customerRequest.getAddress());
        }
        if (StringUtils.isNotBlank(customerRequest.getDate().toString())) {
            user.setDate(customerRequest.getDate());
        }
        if (StringUtils.isNotBlank(customerRequest.getStatus())) {
            user.setStatus(customerRequest.getStatus());
        }

    }

    public List<CustomerReponse> findAllUser() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomerReponse)
                .collect(Collectors.toList());
    }

    public CustomerReponse findUserById(Integer id) {
        CustomerReponse user = customerRepository.findById(id)
                .map(customerMapper::fromCustomerReponse)
                .orElseThrow(() -> new CustomerNotFoundException("ID không tìm thấy user"));
        return user;
    }

    public Boolean existUserById(Integer id) {
        return customerRepository.findById(id).isPresent();
    }

    public String deleteUserById(Integer id) {
        int deleteId = customerRepository.deleteUserById(id);
        if (deleteId > 0) {
            return "Delete Success!!!";
        }
        return "Delete failure!!!";
    }
}
