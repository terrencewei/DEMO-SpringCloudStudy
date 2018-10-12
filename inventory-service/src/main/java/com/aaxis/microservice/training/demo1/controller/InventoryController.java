package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import com.aaxis.microservice.training.demo1.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService mInventoryService;



    @GetMapping("/initData")
    public void initData() {
        logger.info("initData");
        mInventoryService.initData();
    }



    @GetMapping("/{productId}")
    public Inventory findInventory(@PathVariable("productId") String productId) {
        logger.debug("findInventory() for product: {}", productId);
        return mInventoryService.findInventoryById(productId);
    }
}
