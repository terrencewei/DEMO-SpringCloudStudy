package com.aaxis.microservice.training.demo1.controller;

import com.aaxis.microservice.training.demo1.domain.User;
import com.aaxis.microservice.training.demo1.service.UserService;
import com.aaxis.microservice.training.demo1.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController {

    /**
     * If IDE enable lombok plugin, will directly use static 'log' method, this 'logger' will be unnecessary
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService pUserService;

    //    @RequestMapping("/doLogin")
    //    public String login(@ModelAttribute User pUser, HttpServletRequest request) {
    //        logger.info("doLogin:{}", pUser.getUsername());
    //        User user = ((RestUserController) SpringUtil.getBean("restUserController")).login(pUser);
    //        if (user == null) {
    //            request.setAttribute("errorMessage", "Login error");
    //            return "forward:/login";
    //        }
    //        request.getSession().setAttribute("user", user);
    //        return "redirect:/index";
    //    }

    //    @RequestMapping("/logout")
    //    public String logout(HttpServletRequest request) {
    //
    //        request.getSession().removeAttribute("user");
    //
    //        return "redirect:/login";
    //    }

    //    @RequestMapping("/index")
    //    public String index() {
    //        logger.info("index");
    //        return "index";
    //    }
    //
    //
    //
    //    @RequestMapping("/login")
    //    public String login() {
    //        logger.info("login");
    //        return "login";
    //    }
    //
    //
    //
    //    @RequestMapping("/regist")
    //    public String regist() {
    //        logger.info("regist");
    //        return "regist";
    //    }



    @PostMapping("/doRegist")
    public String doRegist(@ModelAttribute User user, RedirectAttributes pRedirectAttributes) {
        logger.info("doRegist:{}", user.getUsername());
        try {
            User u = ((RestUserController) SpringUtil.getBean("restUserController")).doRegist(user);
            pRedirectAttributes.addFlashAttribute("user", u);
        } catch (Exception e) {
            pRedirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/regist";
        }
        // TODO: do Login aand set user to session attr "user"
        return "redirect:/index";
    }
}
