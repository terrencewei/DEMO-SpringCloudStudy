package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Feign service proxy, used in header.jsp
 *
 * Created by terrence on 2018/10/11.
 */
@Service
public class FeignCatalogServiceProxy {

    @Autowired
    private FeignCatalogService mFeignCatalogService;



    public List<Category> findAllCategories() {
        return mFeignCatalogService.findAllCategories();
    }
}