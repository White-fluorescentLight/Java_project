package com.ready.interceptor;

import com.ready.pojo.Result;
import com.ready.utils.CurrentHolder;
import com.ready.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 3. 获取请求头中的token
        String token = request.getHeader("token");

        // 4. 判断token是否存在，如果不存在，说明用户没有登录，返回错误信息(响应401状态码)
        if (token == null || token.isEmpty()) {
            log.warn("请求头中未携带 token，请求路径: {}", request.getRequestURI());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(Result.error("未登录，请先登录")));
            return false;
        }

        // 5. 解析 token
        try {
            Claims claims = JwtUtils.parseJwt(token);
            log.info("token 解析成功，用户信息: {}", claims);

            // 6. 将用户ID存入CurrentHolder
            Object id = claims.get("id");
            if (id != null) {
                Integer userId = ((Number) id).intValue();
                CurrentHolder.setCurrentId(userId);
            }

            return true;
        } catch (Exception e) {
            log.error("token 解析失败: {}", e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(Result.error("token 无效或已过期")));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后移除ID，防止内存泄漏
        CurrentHolder.remove();
    }
}
