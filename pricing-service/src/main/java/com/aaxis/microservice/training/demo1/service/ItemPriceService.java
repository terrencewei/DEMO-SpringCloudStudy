package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.ItemPriceDAO;
import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemPriceService {

    @Autowired
    private ItemPriceDAO mItemPriceDAO;

    public ItemPrice findItemPriceById(String pProductId){
        Optional<ItemPrice> optionalItemPrice = mItemPriceDAO.findById(pProductId);
        if(optionalItemPrice.isPresent()){
            return optionalItemPrice.get();
        }
        return null;
    }

    public void initData(){
        mItemPriceDAO.insertItemPrice();
    }

}
