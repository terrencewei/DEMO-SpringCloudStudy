package com.aaxis.microservice.training.demo1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Category {

    @Id
    @Column(length = 40)
    private String id;
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;



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



    public Set<Product> getProducts() {
        return products;
    }



    public void setProducts(Set<Product> pProducts) {
        products = pProducts;
    }
}
