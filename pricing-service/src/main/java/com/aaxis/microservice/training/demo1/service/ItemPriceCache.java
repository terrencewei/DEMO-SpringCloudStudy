package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.ItemPriceDAO;
import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by terrence on 2018/10/13.
 */
@Component
@Slf4j
public class ItemPriceCache extends RedisCache<ItemPrice> {

    @Autowired
    private ItemPriceDAO mItemPriceDAO;



    @Override
    protected Class getClazz() {
        // TODO: not always invoke this method, should set this class type when @Autowired from @Value via properties config
        return ItemPrice.class;
    }



    @Override
    protected ItemPrice getById(String pId) {
        log.debug("getById() for product: {}", pId);
        Optional<ItemPrice> optionalItemPrice = mItemPriceDAO.findById(pId);
        if (optionalItemPrice.isPresent()) {
            ItemPrice price = optionalItemPrice.get();
            log.debug("getById() found price:{} for product:{}", price.getId(), pId);
            return price;
        }
        log.warn("getById() cannot find price for product:{}", pId);
        return null;

    }

}