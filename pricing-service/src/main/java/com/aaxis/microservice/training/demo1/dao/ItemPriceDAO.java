package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemPriceDAO extends JpaRepository<ItemPrice,String> {

    @Modifying
    @Transactional
    @Query(value = "insert into item_price(id, price) select id,ROUND(rand(1000)*1000,2) from product",nativeQuery = true)
    int insertItemPrice();
}
