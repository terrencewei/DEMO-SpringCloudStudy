package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface InventoryDAO extends JpaRepository<Inventory, String> {

    @Modifying
    @Transactional
    @Query(value = "insert into inventory(id, stock) select id,ROUND(rand(100)*100) from product", nativeQuery = true)
    int addItemInventory();
}
