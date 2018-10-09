package com.terrencewei.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by terrence on 2018/06/12.
 */
@FeignClient(value = "inventory-service")
public interface DemoUserFeignService {

    @GetMapping(path = "/demo-order/{id}")  
    String getOrder(@PathVariable("id") String id);
}