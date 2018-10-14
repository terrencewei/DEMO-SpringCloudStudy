package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.UserDao;
import com.aaxis.microservice.training.demo1.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDao mUserDao;



    public void regist(User pUser) {
        String username = pUser.getUsername();
        log.info("try to regist:{}", username);
        User user = mUserDao.findByUsername(username);
        if (user != null) {
            log.info("User is exists in system:{}", username);
            throw new RuntimeException("User is exists in system");
        }
        log.info("regist:{}", username);
        mUserDao.save(pUser);
    }



    public User findUserByUserName(String pUserName) {
        log.info("try to findUserByUserName:{}", pUserName);
        User user = mUserDao.findByUsername(pUserName);
        if (user != null) {
            log.info("found user:{}", user.getId());
        }
        return user;
    }
}
