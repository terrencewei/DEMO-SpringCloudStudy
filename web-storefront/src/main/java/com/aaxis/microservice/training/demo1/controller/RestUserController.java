package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class RestUserController {

    @Autowired
    private UserService mUserService;

    @Autowired
    private Validator mValidator;



    @RequestMapping("/doLogin")
    public User login(@ModelAttribute User pUser) {
        log.info("doLogin:{}", pUser.getUsername());
        User user = mUserService.findUserByUserName(pUser.getUsername());
        if (user == null || !user.getPassword().equals(pUser.getPassword())) {
            if (user != null) {
                log.info("request user pass:{}, found user pass:{}", pUser.getPassword(), user.getPassword());
            }
            return null;
        }
        return user;
    }



    @PostMapping("/doRegist")
    public User doRegist(@ModelAttribute User user) {
        log.info("doRegist() start regist for user name:{}, pass:{}", user.getUsername(), user.getPassword());
        // validation
        Set<ConstraintViolation<User>> set = mValidator.validate(user);
        if (!set.isEmpty()) {
            String err = set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("<br>"));
            log.error("doRegist() validate error occurs:{}", err);
            throw new RuntimeException(err);
        }
        try {
            mUserService.regist(user);
        } catch (Exception e) {
            log.error("doRegist() regist error occurs:{}", e);
            throw new RuntimeException(e.getMessage());
        }
        log.info("doRegist() regist success for user name:{}", user.getUsername());
        return user;
    }
}
