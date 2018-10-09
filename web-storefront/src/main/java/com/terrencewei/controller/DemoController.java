package com.terrencewei.controller;

import com.terrencewei.service.DemoUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by terrence on 2018/06/12.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoUserFeignService demoFeignService;



    @GetMapping(path = "/feign/{id}")
    public String getOrderByFeign(@PathVariable String id) {
        return demoFeignService.getOrder(id);
    }

}