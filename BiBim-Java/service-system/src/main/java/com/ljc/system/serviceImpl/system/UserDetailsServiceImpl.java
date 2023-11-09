package com.ljc.system.serviceImpl.system;

import com.ljc.model.constants.StatusConst;
import com.ljc.model.enums.ResultCodeEnum;
import com.ljc.model.pojo.User;
import com.ljc.system.custom.CustomUser;
import com.ljc.system.exception.BiBimException;
import com.ljc.system.service.system.RoleService;
import com.ljc.system.service.system.UserRoleService;
import com.ljc.system.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dachengzi
 * @date 2022-12-25 22:49
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserInfoByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 判断用户是否禁用
        if (user.getStatus().intValue() == StatusConst.DISABLE) {
            throw new BiBimException(ResultCodeEnum.USER_ERROR_A0202);
        }

        // 根据userid查询操作权限数据
        List<String> userPermsList = userRoleService.getUserRoleList(user.getId());
        // 转换为Security要求的数据格式
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }

        return new CustomUser(user, authorities);
    }
}
