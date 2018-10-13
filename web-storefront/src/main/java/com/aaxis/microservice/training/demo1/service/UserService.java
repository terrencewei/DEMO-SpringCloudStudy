package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.dao.UserDao;
import com.aaxis.microservice.training.demo1.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao mUserDao;



    public void regist(User pUser) {
        String username = pUser.getUsername();
        logger.info("try to regist:{}", username);
        User user = mUserDao.findByUsername(username);
        if (user != null) {
            logger.info("User is exists in system:{}", username);
            throw new RuntimeException("User is exists in system");
        }
        logger.info("regist:{}", username);
        mUserDao.save(pUser);
    }



    public User findUserByUserName(String pUserName) {
        logger.info("try to findUserByUserName:{}", pUserName);
        User user = mUserDao.findByUsername(pUserName);
        if (user != null) {
            logger.info("found user:{}", user.getId());
        }
        return user;
    }
}
