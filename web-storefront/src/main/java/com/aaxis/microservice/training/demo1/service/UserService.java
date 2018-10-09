package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.UserDao;
import com.aaxis.microservice.training.demo1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDao mUserDao;

    public void regist(User pUser) {
        User user = mUserDao.findByUsername(pUser.getUsername());
        if (user != null){
            throw new RuntimeException("User is exists in system");
        }
        mUserDao.save(pUser);
    }

    public User findUserByUserName(User pUser) {
        User user = mUserDao.findByUsername(pUser.getUsername());
        return user;
    }
}
