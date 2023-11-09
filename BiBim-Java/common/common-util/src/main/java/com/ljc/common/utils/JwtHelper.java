package com.ljc.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author dachengzi
 * @date 2022-10-30 21:59
 */
public class JwtHelper {

    /**
     * token过期时间
     */
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密密钥
    private static String tokenSignKey = "BiBim-dachengzi";

    /**
     * 根据用户id和用户名称创建Token
     *
     * @param userId
     * @param username
     * @return
     */
    public static String createToken(String userId, String username) {
        String token = Jwts.builder()
                //设置分类
                .setSubject("AUTH-USER")
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //设置主体信息
                .claim("userId", userId).claim("username", username)
                //加密编码密钥
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                //压缩字符串
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 从token中获取用户id
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return null;
            }

            //解析token
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String userId = (String) claims.get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //从token获取用户名称
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return "";
            }

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }
}