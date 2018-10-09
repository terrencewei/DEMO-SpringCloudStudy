package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    public List<Product> findProductsByCategory_Id(String categoryId);
}
