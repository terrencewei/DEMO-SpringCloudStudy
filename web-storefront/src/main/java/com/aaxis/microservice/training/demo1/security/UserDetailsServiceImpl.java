package com.aaxis.microservice.training.demo1.security;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by terrence on 2018/10/12.
 */
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService mUserService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername:{}", username);
        UserDetailsImpl userDetails = new UserDetailsImpl();
        User u = new User();
        u.setUsername(username);
        User user = mUserService.findUserByUserName(username);
        if (user == null) {
            logger.info("UsernameNotFoundException:{}", username);
            throw new UsernameNotFoundException(username);
        }
        userDetails.setName(user.getUsername());
        userDetails.setPassword(user.getPassword());
        return userDetails;
    }
}