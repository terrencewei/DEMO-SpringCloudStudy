package com.aaxis.microservice.training.demo1.domain;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ProductResult {

    private Page<Product>   pageProducts;
    private Page<ESProduct> pageProducts_es;
    private Map<String, Object> request = new HashMap<String, Object>();
    private String requestCategoryId;



    public Page<Product> getPageProducts() {
        return pageProducts;
    }



    public void setPageProducts(Page<Product> pPageProducts) {
        pageProducts = pPageProducts;
    }



    public Page<ESProduct> getPageProducts_es() {
        return pageProducts_es;
    }



    public void setPageProducts_es(Page<ESProduct> pPageProducts_es) {
        pageProducts_es = pPageProducts_es;
    }



    public Map<String, Object> getRequest() {
        return request;
    }



    public void setRequest(Map<String, Object> pRequest) {
        request = pRequest;
    }



    public String getRequestCategoryId() {
        return requestCategoryId;
    }



    public void setRequestCategoryId(String pRequestCategoryId) {
        requestCategoryId = pRequestCategoryId;
    }
}
