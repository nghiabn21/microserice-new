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
       return ResponseEntity.ok(userService.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<?> putUser(@RequestBody @Valid CustomerRequest customerRequest){
        userService.updateCustomer(customerRequest);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerReponse>> findAllUser(){
        return ResponseEntity.ok(userService.findAllUser());
    }

    @GetMapping("/exits/{id}")
    public ResponseEntity<Boolean> exitsUserById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.existUserById(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerReponse> findUserById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
