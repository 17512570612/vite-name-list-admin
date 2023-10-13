package com.example.aml.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aml.common.Response;
import com.example.aml.mapper.UserMapper;
import com.example.aml.model.request.UserRequest;
import com.example.aml.model.system.Dict;
import com.example.aml.model.system.User;
import com.example.aml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/list")
    public Response queryUser(UserRequest request) {
        try {
            Page<User> page = new Page<>(request.getPageNo(), request.getPageSize());
            QueryWrapper<User> condition = new QueryWrapper<>();
            Page<User> userPage = userMapper.selectPage(page, condition);
            return Response.success(userPage);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }
}
