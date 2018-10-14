package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.InventoryDAO;
import com.aaxis.microservice.training.demo1.domain.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryDAO mInventoryDAO;

    @Autowired
    private InventoryCache mInventoryCache;



    public void initData() {
        log.info("initData()");
        mInventoryDAO.addItemInventory();
    }



    public Inventory findInventoryById(String pProductId) {
        return mInventoryCache.get("inventory", pProductId);
    }
}
