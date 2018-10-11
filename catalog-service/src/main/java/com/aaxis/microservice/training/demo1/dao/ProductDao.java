package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findProductsByCategoryId(String categoryId);

    Page<Product> findByIdContaining(String id, Pageable var1);

    Page<Product> findByNameContaining(String name, Pageable var1);

    Page<Product> findByIdContainingAndNameContaining(String id, String name, Pageable var1);
}
