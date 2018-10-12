package com.aaxis.microservice.training.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by terrence on 2018/09/28.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    //    @Autowired
    //    private RestTemplateBuilder mRestTemplateBuilder;

    //    @Bean
    //    public RestTemplate restTemplate() {
    //        return mRestTemplateBuilder.build();
    //    }
}
