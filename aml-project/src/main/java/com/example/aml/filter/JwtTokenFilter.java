package com.example.aml.filter;

import com.example.aml.utils.JwtUtils;
import com.example.aml.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    /*
     * @过滤器
     *  1. 从请求头中提取 Token
     *  2. 验证 Token
     *  3. 验证通过，继续处理请求
     *  4. 验证失败，返回错误响应
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // #获取请求路径
        String path = request.getRequestURI();
        // #如果是登录请求或者是 OPTIONS 请求，直接放行
        if (path.equals("/login") || "OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        // #从请求头中获取 Token
        String token = extractTokenFromRequest(request);
        if (token != null) {
            String username = jwtUtils.getUsernameFromToken(token);
            if (username != null && jwtUtils.validateToken(token, username)) {
                // #从 Redis 中获取存储的 Token
                String storedToken = redisUtils.getToken(username);
                // #验证存储的 Token 是否与请求中的 Token 一致
                if (storedToken != null && storedToken.equals(token)) {
                    // #Token 验证通过，继续处理请求
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        // #Token 验证失败，返回错误响应
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(createErrorResponse("Token验证失败：Token无效或过期"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    /*
     * @从请求头中提取 Token
     * @param request 请求
     * @return Token
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    /*
     * @param code 错误码
     * @param message 消息
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("message", message);
        return errorResponse;
    }
}
