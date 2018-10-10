package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "catalog-service")
public interface FeignCatalogService {

    @GetMapping(path = "/api/category/initData")
    void categoryInitData();

    @GetMapping(path = "/api/category/findAllCategories")
    List<Category> findAllCategories();

    @GetMapping(path = "/api/product/initData")
    void productInitData();

    @GetMapping(path = "/api/product/findProductsInPLP")
    Page<Product> findProductsInPLP(@RequestParam String categoryId, @RequestParam(required = false) int page,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue);

    @GetMapping(path = "/api/product/searchProducts")
    Page<Product> searchProducts(@RequestParam(required = false) int page,
            @RequestParam(required = false) String productId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue);
}