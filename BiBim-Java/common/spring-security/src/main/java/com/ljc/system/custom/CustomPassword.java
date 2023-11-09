package com.ljc.system.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dachengzi
 * @date 2022-12-25 22:33
 * 自定义密码组件
 */
@Component
public class CustomPassword implements PasswordEncoder {

    @Bean
    public BCryptPasswordEncoder bCrypt() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return bCrypt().encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return bCrypt().matches(charSequence.toString(),encodedPassword);
    }
}
