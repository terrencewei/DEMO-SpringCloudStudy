package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @Autowired
    private UserService mUserService;

    @Autowired
    private Validator mValidator;



    @RequestMapping("/doLogin")
    public User login(@ModelAttribute User pUser) {
        User user = mUserService.findUserByUserName(pUser);
        if (user == null || !user.getPassword().equals(pUser.getPassword())) {
            return null;
        }
        return user;
    }



    @PostMapping("/doRegist")
    public User doRegist(@ModelAttribute User user) {
        logger.info("doRegist() start regist for user name:{}, pass:{}", user.getUsername(), user.getPassword());
        // validation
        Set<ConstraintViolation<User>> set = mValidator.validate(user);
        if (!set.isEmpty()) {
            String err = set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("<br>"));
            logger.error("doRegist() validate error occurs:{}", err);
            throw new RuntimeException(err);
        }
        try {
            mUserService.regist(user);
        } catch (Exception e) {
            logger.error("doRegist() regist error occurs:{}", e);
            throw new RuntimeException(e.getMessage());
        }
        logger.info("doRegist() regist success for user name:{}", user.getUsername());
        return user;
    }
}
