package com.example.aml.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("SYS_USER")
public class User {
    private Long id; // 用户id
    private String username, password, nickname; // 用户名、密码、昵称、token
    private int jobNumber; // 工号
    private int institution; // 机构号
    private int status; // 状态
    private String roles; // 角色
}
