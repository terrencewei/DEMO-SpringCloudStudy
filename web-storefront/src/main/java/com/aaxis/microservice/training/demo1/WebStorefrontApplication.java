package com.aaxis.microservice.training.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebStorefrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebStorefrontApplication.class, args);
    }



    @Autowired
    private RestTemplateBuilder mRestTemplateBuilder;



    @Bean
    public RestTemplate restTemplate() {
        return mRestTemplateBuilder.build();
    }
}
