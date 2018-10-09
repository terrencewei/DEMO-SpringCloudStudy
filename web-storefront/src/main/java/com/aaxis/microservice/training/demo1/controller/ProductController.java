package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.ProductResult;
import com.aaxis.microservice.training.demo1.service.ProductService;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService mProductService;

    @GetMapping("/initData")
    public String initData(){
        mProductService.initData();
        return "redirect:/login";
    }

    @GetMapping("/searchPage")
    public String loadsSearchPage(){
        return "/search_page";
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request){
        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        int page = request.getParameter("page") == null? 1 : Integer.parseInt(request.getParameter("page"));
        String sortName = request.getParameter("sortName");
        String sortValue = request.getParameter("sortValue");
        ProductResult productResult = ((RestProductController) SpringUtil.getBean("restProductController")).search(productId, name, page, sortName, sortValue);
        request.setAttribute("productResult", productResult);
        return "/search";
    }
}
