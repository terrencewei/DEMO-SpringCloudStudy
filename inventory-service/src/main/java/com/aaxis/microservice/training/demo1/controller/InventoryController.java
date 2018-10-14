package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import com.aaxis.microservice.training.demo1.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService mInventoryService;



    @GetMapping("/initData")
    public void initData() {
        log.info("initData");
        mInventoryService.initData();
    }



    @GetMapping("/{productId}")
    public Inventory findInventory(@PathVariable("productId") String productId) {
        log.debug("findInventory() for product: {}", productId);
        return mInventoryService.findInventoryById(productId);
    }
}
