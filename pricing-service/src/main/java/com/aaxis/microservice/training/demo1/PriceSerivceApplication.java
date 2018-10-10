package com.aaxis.microservice.training.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PriceSerivceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PriceSerivceApplication.class, args);
    }

}
