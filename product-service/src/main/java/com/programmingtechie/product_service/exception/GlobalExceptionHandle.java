package com.programmingtechie.product_service.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ProductsPurchaseException.class)
    public ResponseEntity<String> handle(ProductsPurchaseException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorUserResponse> handle(MethodArgumentNotValidException e){
        HashMap<String, String> hashMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            String name = ((FieldError)objectError).getField();
            String message = objectError.getDefaultMessage();
            hashMap.put(name,message);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorUserResponse(hashMap));
    }
}
