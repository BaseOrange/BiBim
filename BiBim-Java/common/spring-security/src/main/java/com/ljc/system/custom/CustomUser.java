package com.ljc.system.custom;

import com.ljc.model.pojo.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author dachengzi
 * @date 2022-12-25 22:43
 * 自定义用户类
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getSysUser() {
        return user;
    }

    public void setSysUser(User user) {
        this.user = user;
    }
}
