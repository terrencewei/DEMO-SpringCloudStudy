package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Product;
import com.aaxis.microservice.training.demo1.domain.ProductResult;
import com.aaxis.microservice.training.demo1.service.CategoryService;
import com.aaxis.microservice.training.demo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/category")
public class RestCategoryController {

    @Autowired
    private CategoryService mCategoryService;

    @Autowired
    private ProductService mProductService;

    @GetMapping("/initData")
    public String initData() {
        mCategoryService.initData();
        return "redirect:/login";
    }

    @GetMapping(value = {"/{categoryId}/{page}", "/{categoryId}/{page}/{sortName}/{sortValue}"})
    public ProductResult restFindProductsByCategory(@PathVariable("categoryId") String categoryId, @PathVariable(name = "page", required = false) String page, @PathVariable(name = "sortName", required = false) String sortName, @PathVariable(name = "sortValue", required = false) String sortValue) {
        Page<Product> pageProducts = mProductService.findProductsInPLP(categoryId, Integer.parseInt(page), sortName, sortValue);
        ProductResult productResult = new ProductResult();
        productResult.setPageProducts(pageProducts);
        productResult.getRequest().put("requestCategoryId", categoryId);
        productResult.getRequest().put("page", page);
        productResult.getRequest().put("sortName", sortName);
        productResult.getRequest().put("sortValue", sortValue);
        return productResult;
    }

}
