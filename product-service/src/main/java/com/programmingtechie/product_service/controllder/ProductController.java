package com.programmingtechie.product_service.controllder;


import com.programmingtechie.product_service.dto.request.ProductPerchaseRequest;
import com.programmingtechie.product_service.dto.response.ProductPerchaseResponse;
import com.programmingtechie.product_service.dto.request.ProductRequest;
import com.programmingtechie.product_service.dto.response.ProductResponse;
import com.programmingtechie.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPerchaseResponse>> purchaseProducts(@RequestBody @Valid
                                                                          List<ProductPerchaseRequest> requests){
        return ResponseEntity.ok(productService.purchaseProducts(requests));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findByIdProducts(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }
}
