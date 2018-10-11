package com.aaxis.microservice.training.demo1.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    @Email(message = "User name should be email address")
    private String username;
    @Length(min = 7, max = 12, message = "Password should be 7 to 12 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\S]+$", message = "Password should be a mix of lower and uppercase characters as well as numbers")
    private String password;



    public Long getId() {
        return id;
    }



    public void setId(Long pId) {
        id = pId;
    }



    public String getUsername() {
        return username;
    }



    public void setUsername(String pUsername) {
        username = pUsername;
    }



    public String getPassword() {
        return password;
    }



    public void setPassword(String pPassword) {
        password = pPassword;
    }
}
