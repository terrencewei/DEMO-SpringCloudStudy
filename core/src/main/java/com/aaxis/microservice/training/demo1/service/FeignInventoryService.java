package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "inventory-service")
public interface FeignInventoryService {

    @GetMapping(path = "/api/inventory/initData")
    void initData();

    @GetMapping(path = "/api/inventory/{productId}")
    Inventory findInventory(@PathVariable("productId") String productId);
}