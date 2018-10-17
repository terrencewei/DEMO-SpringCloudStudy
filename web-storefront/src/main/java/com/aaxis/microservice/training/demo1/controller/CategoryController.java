package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.ProductResult;
import com.aaxis.microservice.training.demo1.service.CatalogFeignClient;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    @Autowired
    private CatalogFeignClient mCatalogFeignClient;



    @GetMapping("/initData")
    public String initData() {
        mCatalogFeignClient.categoryInitData();
        mCatalogFeignClient.productInitData();
        return "redirect:/login";
    }



    @GetMapping(value = { "/{categoryId}/{page}/{sortName}/{sortValue}", "/{categoryId}/{page}" })
    public String findProductsByCategory(@PathVariable("categoryId") String categoryId,
            @PathVariable(name = "page", required = false) String page,
            @PathVariable(name = "sortName", required = false) String sortName,
            @PathVariable(name = "sortValue", required = false) String sortValue, HttpServletRequest request) {

        ProductResult productResult = ((RestCategoryController) SpringUtil.getBean("restCategoryController"))
                .restFindProductsByCategory(categoryId, page, sortName, sortValue);
        request.setAttribute("productResult", productResult);
        return "/category";
    }

}
