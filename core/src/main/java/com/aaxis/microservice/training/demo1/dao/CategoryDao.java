package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, String> {
}
