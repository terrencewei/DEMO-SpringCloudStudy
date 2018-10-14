package com.aaxis.microservice.training.demo1.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by terrence on 2018/10/13.
 */
@Component
@Slf4j
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        log.info("Login success");
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            request.getSession().setAttribute("user", (UserDetails) principal);
        }
        log.info("login user details:{}", authentication.getDetails());
        // this cleart attr action is necessary if using flash attr in: CustomAuthFailureHandler
        // clearAuthenticationAttributes(request);
        response.sendRedirect("/index");
    }
}