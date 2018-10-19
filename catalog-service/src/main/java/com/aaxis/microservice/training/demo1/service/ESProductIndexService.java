package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.config.ESConfig;
import com.aaxis.microservice.training.demo1.domain.ESProduct;
import com.aaxis.microservice.training.demo1.domain.Product;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrence on 2018/10/18.
 */
@Service
public class ESProductIndexService extends ESIndexService<ESProduct> {

    @Autowired
    private ProductService mProductService;

    @Autowired
    @Qualifier("ESProductConfig")
    private ESConfig mESConfig;



    @Bean(name = "ESProductConfig")
    @ConfigurationProperties(prefix = "elasticsearch.product")
    public ESConfig getProductConfig() {
        return new ESConfig();
    }



    @Override
    protected ESConfig getConfig() {
        return mESConfig;
    }



    @Override
    protected long queryRecordTotal() {
        return mProductService.getProductsCount();
    }



    @Override
    protected List<ESProduct> queryRecords(long pOffset, long pQueryBatchSize) {
        List<Product> products = mProductService.findProductsWithRange(pOffset, pQueryBatchSize);
        List<ESProduct> esProducts = new ArrayList<>(products.size());
        for (Product product : products) {
            ESProduct esProduct = new ESProduct();
            esProduct.setId(product.getId());
            esProduct.setName(product.getName());
            esProduct.setCreatedDate(product.getCreatedDate());
            esProduct.setPriority(product.getPriority());
            esProduct.setCategory_id(product.getCategory() != null ? product.getCategory().getId() : null);
            esProducts.add(esProduct);
        }
        return esProducts;
    }



    @Override
    protected String getRecordId(ESProduct pRecord) {
        return pRecord.getId();
    }



    @Override
    protected String getRecordContent(ESProduct pRecord) {
        return JSONObject.toJSONString(pRecord);
    }

}