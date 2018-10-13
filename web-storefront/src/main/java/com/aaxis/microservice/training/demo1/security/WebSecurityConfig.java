package com.aaxis.microservice.training.demo1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by terrence on 2018/10/12.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthSuccessHandler mCustomAuthSuccessHandler;

    @Autowired
    private CustomAuthFailureHandler mCustomAuthFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler mCustomLogoutSuccessHandler;



    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getUserDetailsService()).passwordEncoder(new PasswordEncoderImpl());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // disable csrf
                .csrf().disable()
                // request
                .authorizeRequests()
                // auth white list
                .antMatchers("/login**").permitAll()//
                .antMatchers("/regist").permitAll()//
                .antMatchers("/doRegist").permitAll()//
                // any other request are in auth black list
                .anyRequest().authenticated()
                // login
                .and().formLogin().loginPage("/login")
                //                .defaultSuccessUrl("/index")
                .successHandler(mCustomAuthSuccessHandler)
                // .failureUrl("/login?failed")
                .failureHandler(mCustomAuthFailureHandler).permitAll()
                // logout
                .and().logout().logoutSuccessHandler(mCustomLogoutSuccessHandler).permitAll()
                //                // auth access to api calls
                //                .and().authorizeRequests().antMatchers("/api/**").authenticated()
                //                // auth type: basic
                //                .and().httpBasic()
                // session
                .and().sessionManagement().maximumSessions(1).expiredUrl("/login?expired");
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.css");
        web.ignoring().antMatchers("/*.js");
    }
}