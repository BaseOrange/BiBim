package com.ljc.model.vo;

import lombok.Data;

/**
 * 登录对象
 * @author dachengzi
 * @date 2023-01-08 21:57
 */
@Data
public class LoginVo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}

