package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Inventory;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "inventory-service", fallbackFactory = InventoryFeignClient.InventoryHystrixFactory.class)
public interface InventoryFeignClient {

    @GetMapping(path = "/api/inventory/initData")
    void initData();

    @GetMapping(path = "/api/inventory/{productId}")
    Inventory findInventory(@PathVariable("productId") String productId);

    @Component
    @Slf4j
    static class InventoryHystrixFactory implements FallbackFactory<InventoryFeignClient> {

        @Override
        public InventoryFeignClient create(Throwable cause) {
            return new InventoryFeignClient() {
                @Override
                public void initData() {
                    log.info("Hystrix initData()");
                }



                @Override
                public Inventory findInventory(String productId) {
                    log.info("Hystrix findInventory() productId:{}", productId);
                    Inventory inventory = new Inventory();
                    inventory.setId(productId);
                    return inventory;
                }
            };
        }
    }
}