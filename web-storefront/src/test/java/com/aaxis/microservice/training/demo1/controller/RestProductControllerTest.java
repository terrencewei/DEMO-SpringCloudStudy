package com.aaxis.microservice.training.demo1.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by terrence on 2018/10/11.
 *
 * @see RestProductController
 */
//@RunWith(SpringRunner.class)
//@WebMvcTest
public class RestProductControllerTest {

    @Autowired
    private MockMvc mvc;



//    @Test
    public void test() throws Exception {
        mvc.perform(get("/rest/product/search").param("productId", "A_1")).andExpect(status().isOk()).andDo(print());
    }
}