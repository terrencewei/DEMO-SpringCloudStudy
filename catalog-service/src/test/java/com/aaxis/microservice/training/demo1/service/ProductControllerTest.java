package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by terrence on 2018/10/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = { "test.local:inventory-service:+:stubs:8082",
        "test.local:pricing-service:+:stubs:8081" }, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@Slf4j
public class ProductControllerTest {

    @Autowired
    private RestTemplate mRestTemplate;



    @Test
    public void test() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");

        Inventory inventory = (Inventory) mRestTemplate
                .getForObject("http://localhost:8082/api/inventory/test1", Inventory.class);
        ItemPrice price = (ItemPrice) mRestTemplate
                .getForObject("http://localhost:8081/api/price/A_1", ItemPrice.class);

        assertThat(inventory).extracting(Inventory::getId, Inventory::getStock).containsExactly("test1", 333);
        assertThat(price).extracting(ItemPrice::getId, ItemPrice::getPrice).containsExactly("A_1", 338.33);
    }

}