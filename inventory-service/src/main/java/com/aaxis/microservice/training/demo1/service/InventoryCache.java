package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.InventoryDAO;
import com.aaxis.microservice.training.demo1.domain.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by terrence on 2018/10/13.
 */
@Component
@Slf4j
public class InventoryCache extends RedisCache<Inventory> {

    @Autowired
    private InventoryDAO mInventoryDAO;



    @Override
    protected Class getClazz() {
        // TODO: not always invoke this method, should set this class type when @Autowired from @Value via properties config
        return Inventory.class;
    }



    @Override
    protected Inventory getById(String pId) {
        log.debug("getById() for product: {}", pId);
        Optional<Inventory> optionalInventory = mInventoryDAO.findById(pId);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            log.debug("getById() found inventory:{} for product:{}", inventory.getId(), pId);
            return inventory;
        }
        log.warn("getById() cannot find inventory for product:{}", pId);
        return null;
    }

}