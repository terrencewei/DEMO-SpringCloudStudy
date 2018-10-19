package com.aaxis.microservice.training.demo1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

    @Id
    @Column(length = 40)
    private String id;
    private int    stock;



    public String getId() {
        return id;
    }



    public void setId(String pId) {
        id = pId;
    }



    public int getStock() {
        return stock;
    }



    public void setStock(int pStock) {
        stock = pStock;
    }
}
