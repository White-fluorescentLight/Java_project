package com.ready.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT令牌操作工具类
 */
public class JwtUtils {

    // 密钥（和测试类中保持一致）
    private static final String SECRET_KEY = "cmVhZHk=";
    // 令牌过期时间：12小时（单位：毫秒）
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L;

    /**
     * 生成JWT令牌
     * @param claims 自定义信息
     * @return 生成的JWT字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                // 添加自定义信息
                .addClaims(claims)
                // 设置加密算法和密钥
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                // 设置过期时间：当前时间 + 12小时
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                // 生成令牌
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌字符串
     * @return 解析后的Claims对象（包含自定义信息）
     */
    public static Claims parseJwt(String token) {
        return Jwts.parser()
                // 指定解析密钥（必须和生成时一致）
                .setSigningKey(SECRET_KEY)
                // 解析令牌并获取数据体
                .parseClaimsJws(token)
                .getBody();
    }
}
