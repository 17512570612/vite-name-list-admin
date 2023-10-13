package com.example.aml.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("SYS_DICT_ITEM")
public class DictItem {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String relatedId;
    private String code;
    private String value;
    private String label;
    private int sort;
}
