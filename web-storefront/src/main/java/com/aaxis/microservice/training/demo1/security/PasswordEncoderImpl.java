package com.aaxis.microservice.training.demo1.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by terrence on 2018/10/12.
 */
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (StringUtils.isNotBlank(rawPassword)) {
            return rawPassword.toString();
        }
        return null;
    }



    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(rawPassword) || StringUtils.isBlank(encodedPassword)) {
            return false;
        }
        return encodedPassword.equals(rawPassword.toString());
    }
}