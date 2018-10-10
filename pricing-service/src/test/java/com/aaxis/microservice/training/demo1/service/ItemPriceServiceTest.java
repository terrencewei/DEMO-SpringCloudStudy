package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.ItemPriceDAO;
import com.aaxis.microservice.training.demo1.service.ItemPriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by terrence on 2018/10/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemPriceServiceTest {

    @Autowired
    ItemPriceService mItemPriceService;

    @Autowired
    private ItemPriceDAO mItemPriceDAO;

    @Autowired
    private Environment env;



    @Test
    public void initData() throws Exception {
        mItemPriceService.initData();
        assertThat(mItemPriceDAO.findAll()).isNotEmpty();
    }

}