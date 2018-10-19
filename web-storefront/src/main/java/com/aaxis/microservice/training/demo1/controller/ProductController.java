package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.ProductResult;
import com.aaxis.microservice.training.demo1.service.CatalogFeignClient;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    @Autowired
    private CatalogFeignClient mCatalogFeignClient;



    @GetMapping("/initData")
    public String initData() {
        mCatalogFeignClient.productInitData();
        return "redirect:/login";
    }



    @GetMapping("/searchPage")
    public String loadsSearchPage() {
        return "/search_page";
    }



    @GetMapping("/searchPage_es")
    public String loadsSearchPage_es() {
        return "/search_page_es";
    }



    @GetMapping("/search")
    public String search(HttpServletRequest request) {
        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        String sortName = request.getParameter("sortName");
        String sortValue = request.getParameter("sortValue");
        ProductResult productResult = ((RestProductController) SpringUtil.getBean("restProductController"))
                .search(productId, name, page, sortName, sortValue);
        request.setAttribute("productResult", productResult);
        return "/search";
    }



    @GetMapping("/search_es")
    public String search_es(HttpServletRequest request) {
        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        String sortName = request.getParameter("sortName");
        String sortValue = request.getParameter("sortValue");
        ProductResult productResult = ((RestProductController) SpringUtil.getBean("restProductController"))
                .search_es(productId, name, page, sortName, sortValue);
        request.setAttribute("productResult", productResult);
        return "/search_es";
    }

}
