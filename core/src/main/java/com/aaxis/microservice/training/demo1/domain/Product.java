package com.aaxis.microservice.training.demo1.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Product {

    @Id
    @Column(length = 40)
    private String id;
    private String name;
    private Integer priority;
    private Date createdDate;

    @Transient
    private double price;
    @Transient
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double pPrice) {
        price = pPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category pCategory) {
        category = pCategory;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int pStock) {
        stock = pStock;
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
}
