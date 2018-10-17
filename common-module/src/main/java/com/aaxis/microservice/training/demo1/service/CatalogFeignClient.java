package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.domain.Product;
import com.aaxis.microservice.training.demo1.domain.RestPageImpl;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "catalog-service", fallbackFactory = CatalogFeignClient.CatalogHystrixFactory.class)
public interface CatalogFeignClient {

    @GetMapping(path = "/api/category/initData")
    void categoryInitData();

    @GetMapping(path = "/api/category/findAllCategories")
    List<Category> findAllCategories();

    @GetMapping(path = "/api/product/initData")
    void productInitData();

    @GetMapping(path = "/api/product/findProductsInPLP")
    RestPageImpl<Product> findProductsInPLP(@RequestParam String categoryId, @RequestParam(required = false) int page,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue);

    @GetMapping(path = "/api/product/searchProducts")
    RestPageImpl<Product> searchProducts(@RequestParam(required = false) int page,
            @RequestParam(required = false) String productId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortName, @RequestParam(required = false) String sortValue);

    @Component
    @Slf4j
    static class CatalogHystrixFactory implements FallbackFactory<CatalogFeignClient> {

        @Override
        public CatalogFeignClient create(Throwable cause) {
            return new CatalogFeignClient() {

                @Override
                public void categoryInitData() {
                    log.info("Hystrix categoryInitData()");
                }



                @Override
                public List<Category> findAllCategories() {
                    log.info("Hystrix initfindAllCategoriesData()");
                    return Collections.emptyList();
                }



                @Override
                public void productInitData() {
                    log.info("Hystrix productInitData()");
                }



                @Override
                public RestPageImpl<Product> findProductsInPLP(String categoryId, int page, String sortName,
                        String sortValue) {
                    log.info("Hystrix findProductsInPLP()");
                    return new RestPageImpl<Product>();
                }



                @Override
                public RestPageImpl<Product> searchProducts(int page, String productId, String name, String sortName,
                        String sortValue) {
                    log.info("Hystrix searchProducts()");
                    return new RestPageImpl<Product>();
                }
            };
        }
    }
}