package com.ljc.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljc.common.result.Result;
import com.ljc.common.utils.IpUtil;
import com.ljc.model.enums.ResultCodeEnum;
import com.ljc.common.utils.JwtHelper;
import com.ljc.common.utils.ResponseUtil;
import com.ljc.model.vo.LoginVo;
import com.ljc.system.custom.CustomUser;
import com.ljc.system.service.LoginLogService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Token登录过滤器
 *
 * @author dachengzi
 * @date 2023-01-08 21:36
 */
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 构造
     *
     * @param authenticationManager
     */
    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate, LoginLogService loginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/system/admin/login", "POST"));
        this.redisTemplate = redisTemplate;
        this.loginLogService = loginLogService;
    }

    /**
     * 获取用户名和密码，认证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            Authentication authenticate = this.getAuthenticationManager().authenticate(authenticationToken);
            return authenticate;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 认证成功处理方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取认证对象
        CustomUser customUser = (CustomUser) authResult.getPrincipal();

        // 保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        //生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId().toString(), customUser.getSysUser().getUsername());

        // 记录登录日志信息
        loginLogService.recordLoginLog(customUser.getUsername(), 0, IpUtil.getIpAddress(request), "登录成功");

        //返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 认证失败的方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 用户禁用已经判断了在后面，这里只存在用户删除和用户密码错误，用户名不正确
        if (failed.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build("未知错误，请联系管理员", ResultCodeEnum.USER_ERROR_A0200));
            // 记录登录日志信息
            loginLogService.recordLoginLog(null, 1, IpUtil.getIpAddress(request), "登录失败,未知错误");
        } else {
            log.error("登录失败：{}", failed);
            ResponseUtil.out(response, Result.build("账户名或密码错误", ResultCodeEnum.USER_ERROR_A0200));
            // 记录登录日志信息
            loginLogService.recordLoginLog(null, 1, IpUtil.getIpAddress(request), "登录失败,账户名或密码错误");
        }
    }
}
