package com.example.aml.controller;

import com.example.aml.common.Response;
import com.example.aml.model.system.User;
import com.example.aml.service.impl.UserServiceImpl;
import com.example.aml.utils.JwtUtils;
import com.example.aml.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RedisUtils redisUtils;

    /*
     * @用户登录
     * @param params 用户名和密码
     * @return 用户信息和 Token
     */
    @PostMapping("/login")
    public Response login(@RequestBody User params) {
        try {
            String username = params.getUsername();
            String password = params.getPassword();
            User user = userServiceImpl.findByUsername(username);
            if (user == null) return Response.fail(400, null,"用户不存在");
            if (user.getPassword().equals(password)) {
                // #生成 Token
                String token = jwtUtils.generateToken(username);
                // #将 Token 存储到 Redis 中
                redisUtils.setToken(username, token);
                // #将 Token 和 用户信息 添加到响应体中
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("user", user);
                responseBody.put("token", token);
                return Response.success(responseBody);
            } else {
                return Response.fail(400, null,"密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }
}
