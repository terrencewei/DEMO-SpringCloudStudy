package com.aaxis.microservice.training.demo1.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by terrence on 2018/10/13.
 */
@Component
@Slf4j
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String AUTHENTICATION_MESSAGE = "FLASH_AUTHENTICATION_MESSAGE";



    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // if user not found, exp will be thrown from:
        // com.aaxis.microservice.training.demo1.security.UserDetailsServiceImpl.loadUserByUsername()
        // then caught by:
        // org.springframework.security.authentication.dao.DaoAuthenticationProvider
        // then convert to BadCredentialsException and thrown by:
        // org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider

        // if user pass is not right, BadCredentialsException will be thrown from:
        // org.springframework.security.authentication.dao.DaoAuthenticationProvider

        if (exception != null) {
            final FlashMap flashMap = new FlashMap();
            // Don't send the AuthenticationException object itself because it has no default constructor and cannot be re-instantiated.
            flashMap.put(AUTHENTICATION_MESSAGE, exception.getMessage());
            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
        }
        log.info("Login failed");
        // Using flash attribute instrand of Spring OOTB session attribute to remove the error message onece user fresh the page
        // saveException(request, exception);
        response.sendRedirect("/login?error");
    }

}