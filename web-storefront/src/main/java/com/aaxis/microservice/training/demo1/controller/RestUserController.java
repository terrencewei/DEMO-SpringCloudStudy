package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    @Autowired
    private UserService pUserService;

    @RequestMapping("/doLogin")
    public User login(@ModelAttribute User pUser){
        User user = pUserService.findUserByUserName(pUser);
        if(user == null || !user.getPassword().equals(pUser.getPassword())){
            return null;
        }
        return user;
    }

    @PostMapping("/doRegist")
    public User doRegist(@ModelAttribute User user){
        // validation, TODO

        try{
            pUserService.regist(user);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }
}
