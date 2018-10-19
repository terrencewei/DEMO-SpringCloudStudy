package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.ESProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by terrence on 2018/10/17.
 */
public interface ESProductDao extends ElasticsearchRepository<ESProduct, String> {

    Page<ESProduct> findByIdContainingAndNameContaining(String id, String name, Pageable var1);
}
