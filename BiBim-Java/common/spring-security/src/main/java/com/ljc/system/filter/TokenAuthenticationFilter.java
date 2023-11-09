package com.ljc.system.filter;

import com.alibaba.fastjson.JSON;
import com.ljc.common.result.Result;
import com.ljc.model.enums.ResultCodeEnum;
import com.ljc.common.utils.JwtHelper;
import com.ljc.common.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 认证解析token过滤器
 *
 * @author dachengzi
 * @date 2023-01-08 23:03
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private RedisTemplate<String,String> redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // 登录接口直接放行
        if ("/system/admin/login".equals(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);
        if (null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            //访问未授权
            ResponseUtil.out(httpServletResponse, Result.build(null, ResultCodeEnum.USER_ERROR_A0301));
        }
    }

    /**
     * token解析，查询是否有效
     *
     * @param httpServletRequest
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest) {
        //token置于header中
        String token = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(token)) {
            String username = JwtHelper.getUsername(token);
            String userId = JwtHelper.getUserId(token).toString();
            if (!StringUtils.isEmpty(username)) {
                // 获取用户权限集合
                String authoritiesString = redisTemplate.opsForValue().get(username);
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (Map map : mapList) {
                    authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
                }
                return new UsernamePasswordAuthenticationToken(userId, username, authorities);
            }
        }
        return null;
    }
}
