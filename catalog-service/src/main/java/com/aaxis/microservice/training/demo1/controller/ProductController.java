package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Product;
import com.aaxis.microservice.training.demo1.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService mProductService;



    @GetMapping("/initData")
    public void initData() {
        logger.info("initData");
        mProductService.initData();
    }



    @GetMapping("/findProductsInPLP")
    public Page<Product> findProductsInPLP(@RequestParam String categoryId, @RequestParam(required = false) int page,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue) {
        logger.debug("findProductsInPLP() categoryId:{}, page:{}, sortName:{}, sortValue:{}", categoryId, page,
                sortName, sortValue);
        return mProductService.findProductsInPLP(categoryId, page, sortName, sortValue);
    }



    @GetMapping("/searchProducts")
    public Page<Product> searchProducts(@RequestParam(required = false) int page,
            @RequestParam(required = false) String productId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue) {
        logger.debug("findProductsInPLP() page:{}, productId:{}, name:{}, sortName:{}, sortValue:{}", page, productId,
                name, sortName, sortValue);
        return mProductService.searchProducts(page, productId, name, sortName, sortValue);
    }

}
