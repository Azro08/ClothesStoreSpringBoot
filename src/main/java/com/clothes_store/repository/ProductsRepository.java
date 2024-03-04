package com.clothes_store.repository;

import com.clothes_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
    List<Product> getProductsByCategory(String category);
}
