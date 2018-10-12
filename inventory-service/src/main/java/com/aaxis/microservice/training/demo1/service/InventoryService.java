package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.InventoryDAO;
import com.aaxis.microservice.training.demo1.domain.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class InventoryService {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryDAO mInventoryDAO;



    public void initData() {
        logger.info("initData()");
        mInventoryDAO.addItemInventory();
    }



    public Inventory findInventoryById(String pProductId) {
        logger.debug("findInventoryById() for product: {}", pProductId);
        Optional<Inventory> optionalInventory = mInventoryDAO.findById(pProductId);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            logger.debug("findInventoryById() found inventory:{} for product:{}", inventory.getId(), pProductId);
            return inventory;
        }
        logger.warn("findInventoryById() cannot find inventory for product:{}", pProductId);
        return null;
    }
}
