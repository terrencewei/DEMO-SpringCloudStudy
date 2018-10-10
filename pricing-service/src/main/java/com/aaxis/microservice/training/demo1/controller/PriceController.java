package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import com.aaxis.microservice.training.demo1.service.ItemPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("price")
public class PriceController {

    @Autowired
    private ItemPriceService mItemPriceService;

    @GetMapping("/initData")
    public void initData(){
        mItemPriceService.initData();
    }

    @GetMapping("/{productId}")
    public ItemPrice findPrice( @PathVariable("productId") String productId){
        return mItemPriceService.findItemPriceById(productId);
    }
}
