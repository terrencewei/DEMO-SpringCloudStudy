package com.terrencewei.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

/**
 * Created by terrence on 2018/09/28.
 */
@RestController
public class DemoInventoryServiceProvider {

    @Value("${server.port}")
    String port;



    @GetMapping(path = "/inventory/{id}")
    public String demo(@PathVariable String id) {
        return MessageFormat.format("This is response from /inventory-service/inventory/{0}, port:{1}", id, port);
    }
}