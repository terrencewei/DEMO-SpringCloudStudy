package com.aaxis.microservice.training.demo1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ItemPrice implements Serializable {
    
    @Id
    @Column(length = 40)
    private String id;
    private double price;



    public String getId() {
        return id;
    }



    public void setId(String pId) {
        id = pId;
    }



    public double getPrice() {
        return price;
    }



    public void setPrice(double pPrice) {
        price = pPrice;
    }
}
