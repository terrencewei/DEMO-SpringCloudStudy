package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.ItemPriceDAO;
import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ItemPriceService {

    @Autowired
    private ItemPriceDAO mItemPriceDAO;

    @Autowired
    private ItemPriceCache mItemPriceCache;



    public ItemPrice findItemPriceById(String pProductId) {
        return mItemPriceCache.get("price", pProductId);
    }



    public void initData() {
        log.info("initData()");
        mItemPriceDAO.insertItemPrice();
    }

}
