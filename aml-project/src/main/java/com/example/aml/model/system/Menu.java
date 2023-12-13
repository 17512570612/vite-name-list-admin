package com.example.aml.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("SYS_MENU")
public class Menu {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String title,name, path, icon; // 菜单名、菜单地址、菜单图标
    private String parentId; // 父菜单id
    private Integer sort; // 菜单排序
    private String component; // 组件
    private Integer type; // 菜单类型
    private Integer status; // 菜单状态
    private Integer isParent; // 是否父节点
    @TableField(exist = false)
    private List<Menu> children; // 子菜单
}
