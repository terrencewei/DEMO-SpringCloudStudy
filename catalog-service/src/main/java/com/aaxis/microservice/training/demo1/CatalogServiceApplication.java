package com.aaxis.microservice.training.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by terrence on 2018/09/28.
 */
@SpringBootApplication
//@EnableEurekaClient
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }



    @Autowired
    private RestTemplateBuilder mRestTemplateBuilder;
    


    @Bean
    public RestTemplate restTemplate() {
        return mRestTemplateBuilder.build();
    }
}
