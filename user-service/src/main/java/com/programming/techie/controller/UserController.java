package com.programming.techie.controller;

import com.programming.techie.dto.CustomerReponse;
import com.programming.techie.dto.CustomerRequest;
import com.programming.techie.entity.User;
import com.programming.techie.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody @Valid CustomerRequest customerRequest){
       return ResponseEntity.status(HttpStatus.CREATED).body(userService.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<?> putUser(@RequestBody @Valid CustomerRequest customerRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateCustomer(customerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerReponse>> findAllUser(){
        return ResponseEntity.ok(userService.findAllUser());
    }

    @GetMapping("/exits/{id}")
    public ResponseEntity<Boolean> exitsUserById(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(userService.existUserById(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerReponse> findUserById(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
