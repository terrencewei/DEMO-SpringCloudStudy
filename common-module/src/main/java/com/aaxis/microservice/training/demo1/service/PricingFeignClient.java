package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.ItemPrice;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by terrence on 2018/10/10.
 */
@FeignClient(value = "pricing-service", fallbackFactory = PricingFeignClient.PricingHystrixFactory.class)
public interface PricingFeignClient {

    @GetMapping(path = "/api/price/initData")
    void initData();

    @GetMapping(path = "/api/price/{productId}")
    ItemPrice findPrice(@PathVariable("productId") String productId);

    @Component
    @Slf4j
    static class PricingHystrixFactory implements FallbackFactory<PricingFeignClient> {

        @Override
        public PricingFeignClient create(Throwable cause) {
            return new PricingFeignClient() {
                @Override
                public void initData() {
                    log.debug("Hystrix initData()");
                }



                @Override
                public ItemPrice findPrice(String productId) {
                    log.debug("Hystrix findPrice() productId:{}", productId);
                    ItemPrice price = new ItemPrice();
                    price.setId(productId);
                    return price;
                }
            };
        }
    }
}