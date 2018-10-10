package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "pricing-service")
public interface FeignPricingService {

    @GetMapping(path = "/api/price/initData")
    void initData();

    @GetMapping(path = "/api/price/{productId}")
    ItemPrice findPrice(@PathVariable("productId") String productId);
}