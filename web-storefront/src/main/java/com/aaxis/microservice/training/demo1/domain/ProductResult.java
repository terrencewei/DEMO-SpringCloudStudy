package com.aaxis.microservice.training.demo1.domain;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ProductResult {
    private Page<Product> mPageProducts;

    private Map<String, Object> request = new HashMap<String, Object>();

    private String requestCategoryId;

    public Page<Product> getPageProducts() {
        return mPageProducts;
    }

    public void setPageProducts(Page<Product> pPageProducts) {
        mPageProducts = pPageProducts;
    }

    public String getRequestCategoryId() {
        return requestCategoryId;
    }

    public void setRequestCategoryId(String pRequestCategoryId) {
        requestCategoryId = pRequestCategoryId;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public void setRequest(Map<String, Object> pRequest) {
        request = pRequest;
    }
}
