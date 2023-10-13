package com.example.aml.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("SYS_DICT")
public class Dict {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String code;
    private String value;
    private String description;
    @TableField(exist = false)
    private List<DictItem> list;
}
