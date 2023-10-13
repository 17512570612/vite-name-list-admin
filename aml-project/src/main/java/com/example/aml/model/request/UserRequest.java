package com.example.aml.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private int range; // 查询范围, 1.机构下所有用户 2.当前机构用户
    private Long id; // 用户id
    private String username, nickname;
    private int jobNumber; // 工号
    private int institution; // 机构号
    private int status; // 状态
    private String roles; // 角色
    private int pageNo;
    private int pageSize;
}
