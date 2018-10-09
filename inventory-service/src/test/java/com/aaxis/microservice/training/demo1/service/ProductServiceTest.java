package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by terrence on 2018/10/09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService mProductService;

    @Autowired
    private Environment env;



    @Test
    public void initData() throws Exception {
        mProductService.initData();
        List<String> categoryIds = Arrays.asList(env.getProperty("categoryIds").split(","));
        for (String categoryId : categoryIds) {
            List<Product> products = mProductService.findProductsByCategoryId(categoryId);
            assertThat(products).isNotEmpty();
        }
    }

}