package com.example.aml.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aml.model.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}