package com.aaxis.microservice.training.demo1.domain;

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
    private Set<Product> mProducts;



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
}
