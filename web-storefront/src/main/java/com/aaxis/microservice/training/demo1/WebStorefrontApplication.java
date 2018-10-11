package com.aaxis.microservice.training.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class WebStorefrontApplication extends SpringBootServletInitializer {

    @Autowired
    private RestTemplateBuilder mRestTemplateBuilder;



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebStorefrontApplication.class);
    }



    @Bean
    public RestTemplate restTemplate() {
        return mRestTemplateBuilder.build();
    }



    public static void main(String[] args) {
        SpringApplication.run(WebStorefrontApplication.class, args);
    }

}
