package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService pUserService;



    @RequestMapping("/logtest")
    public String logtest() {
        log.info("this is log info");
        log.warn("this is log warn");
        log.error("this is log error");
        log.debug("this is log debug");
        log.trace("this is log trace");
        return "index";
    }



    @PostMapping("/doRegist")
    public String doRegist(@ModelAttribute User user, RedirectAttributes pRedirectAttributes) {
        log.info("doRegist:{}", user.getUsername());
        try {
            User u = ((RestUserController) SpringUtil.getBean("restUserController")).doRegist(user);
            pRedirectAttributes.addFlashAttribute("user", u);
        } catch (Exception e) {
            pRedirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/regist";
        }
        return "redirect:/index";
    }
}
