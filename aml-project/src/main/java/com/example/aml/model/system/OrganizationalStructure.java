package com.example.aml.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_ORGANIZATIONAL_STRUCTURE")
public class OrganizationalStructure {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String parentId;
    private String name;
    private int sort;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String personInCharge;
    private String telephone;
    private String email;
}
