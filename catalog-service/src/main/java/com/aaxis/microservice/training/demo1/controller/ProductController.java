package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Product;
import com.aaxis.microservice.training.demo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService mProductService;



    @GetMapping("/initData")
    public void initData() {
        mProductService.initData();
    }



    @GetMapping("/findProductsInPLP")
    public Page<Product> findProductsInPLP(@RequestParam String categoryId, @RequestParam(required = false) int page,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue) {
        return mProductService.findProductsInPLP(categoryId, page, sortName, sortValue);
    }



    @GetMapping("/searchProducts")
    public Page<Product> searchProducts(@RequestParam(required = false) int page,
            @RequestParam(required = false) String productId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue) {
        return mProductService.searchProducts(page, productId, name, sortName, sortValue);
    }

}
