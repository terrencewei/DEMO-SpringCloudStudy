package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import com.aaxis.microservice.training.demo1.service.ItemPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
@Slf4j
public class PriceController {

    @Autowired
    private ItemPriceService mItemPriceService;



    @GetMapping("/initData")
    public void initData() {
        log.info("initData");
        mItemPriceService.initData();
    }



    @GetMapping("/{productId}")
    public ItemPrice findPrice(@PathVariable("productId") String productId) {
        log.debug("findPrice() for product: {}", productId);
        return mItemPriceService.findItemPriceById(productId);
    }
}
