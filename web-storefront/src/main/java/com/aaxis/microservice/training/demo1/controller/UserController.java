package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService pUserService;

    @RequestMapping("/doLogin")
    public String login(@ModelAttribute User pUser, HttpServletRequest request){
        User user = ((RestUserController) SpringUtil.getBean("restUserController")).login(pUser);
        if(user == null){
            request.setAttribute("errorMessage", "Login error");
            return "forward:/login";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){

        request.getSession().removeAttribute("user");

        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/regist")
    public String regist(){
        return "regist";
    }

    @PostMapping("/doRegist")
    public String doRegist(@ModelAttribute User user, HttpServletRequest request){
        // validation, TODO

        try{
            User u = ((RestUserController) SpringUtil.getBean("restUserController")).doRegist(user);
        } catch (Exception e){
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            return "forward:/regist";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/index";
    }
}
