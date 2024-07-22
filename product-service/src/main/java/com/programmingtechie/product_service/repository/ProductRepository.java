package com.programmingtechie.product_service.repository;

import com.programmingtechie.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}