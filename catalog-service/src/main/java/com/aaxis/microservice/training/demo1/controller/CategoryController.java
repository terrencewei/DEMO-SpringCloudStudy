package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.Category;
import com.aaxis.microservice.training.demo1.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService mCategoryService;



    @GetMapping("/initData")
    public void initData() {
        log.info("initData");
        mCategoryService.initData();
    }



    @GetMapping("/findAllCategories")
    public List<Category> findAllCategories() {
        log.info("findAllCategories");
        return mCategoryService.findAllCategories();
    }

}
