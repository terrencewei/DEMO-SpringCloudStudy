package com.aaxis.microservice.training.demo1.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by terrence on 2018/10/12.
 */
public class UserDetailsImpl implements UserDetails {

    private Integer id;
    private String  name;
    private String  password;
    Collection<GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }



    @Override
    public String getUsername() {
        return name;

    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }



    public Integer getId() {
        return id;
    }



    public void setId(Integer pId) {
        id = pId;
    }



    public String getName() {
        return name;
    }



    public void setName(String pName) {
        name = pName;
    }



    public void setPassword(String pPassword) {
        password = pPassword;
    }



    public void setAuthorities(Collection<GrantedAuthority> pAuthorities) {
        authorities = pAuthorities;
    }
}