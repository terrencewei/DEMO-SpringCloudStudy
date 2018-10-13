package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by terrence on 2018/10/11.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RestUserController.class)
public class RestUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService mUserService;

    @MockBean
    private Validator mValidator;



    @Test
    public void login() throws Exception {
        String name = "test1@example.com";
        String pass = "123abcABC";
        String responseString = mvc.perform(get("/rest/doLogin").flashAttr("user", getUser(name, pass))).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(responseString).isBlank();
    }



    @Test
    public void doRegist() throws Exception {
        String name = "test1@example.com";
        String pass = "123abcABC";
        String responseString = mvc.perform(post("/rest/doRegist").flashAttr("user", getUser(name, pass)))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(responseString).isNotBlank();
        User response = JSONObject.parseObject(responseString, User.class);
        assertThat(response).extracting(User::getUsername, User::getPassword).containsExactly(name, pass);
    }



    private User getUser(String name, String pass) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        return user;
    }

}