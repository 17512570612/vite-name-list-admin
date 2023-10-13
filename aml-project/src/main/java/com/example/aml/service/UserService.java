package com.example.aml.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.model.system.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}