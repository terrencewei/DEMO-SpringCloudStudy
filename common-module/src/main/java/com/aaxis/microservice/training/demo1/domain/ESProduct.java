package com.aaxis.microservice.training.demo1.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by terrence on 2018/10/17.
 *
 * @see com.aaxis.microservice.training.demo1.domain.Product
 */
@Document(indexName = "product", type = "default", shards = 1, replicas = 0)
public class ESProduct {

    @Id
    private String  id;
    private String  name;
    private Integer priority;
    private Date    createdDate;
    private double  price;
    private int     stock;
    private String  category_id;



    public String getId() {
        return id;
    }



    public void setId(String pId) {
        id = pId;
    }



    public String getName() {
        return name;
    }



    public void setName(String pName) {
        name = pName;
    }



    public Integer getPriority() {
        return priority;
    }



    public void setPriority(Integer pPriority) {
        priority = pPriority;
    }



    public Date getCreatedDate() {
        return createdDate;
    }



    public void setCreatedDate(Date pCreatedDate) {
        createdDate = pCreatedDate;
    }



    public double getPrice() {
        return price;
    }



    public void setPrice(double pPrice) {
        price = pPrice;
    }



    public int getStock() {
        return stock;
    }



    public void setStock(int pStock) {
        stock = pStock;
    }



    public String getCategory_id() {
        return category_id;
    }



    public void setCategory_id(String pCategory_id) {
        category_id = pCategory_id;
    }
}