package com.aaxis.microservice.training.demo1.service;


import com.aaxis.microservice.training.demo1.dao.InventoryDAO;
import com.aaxis.microservice.training.demo1.domain.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryService {

    @Autowired
    private InventoryDAO mInventoryDAO;

    public void initData(){
        mInventoryDAO.addItemInventory();
    }

    public Inventory findInventoryById(String pProductId){
        Optional<Inventory> optionalInventory = mInventoryDAO.findById(pProductId);
        if(optionalInventory.isPresent()){
            return optionalInventory.get();
        }
        return null;
    }
}
