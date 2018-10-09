package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    public User findByUsername(String username);
}
