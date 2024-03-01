package com.foodsupply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodsupply.model.Product;

public interface productRepository extends JpaRepository<Product, Long> {

}
