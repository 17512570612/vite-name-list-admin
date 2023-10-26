package com.example.aml.model.country;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("FILTER_COUNTRY_RECORD_FLOW")
public class FilterCountryRecordFlow {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id; // #用户id
    private String source; // #制裁来源
    private String operate; // #操作类型
    private String businessManagementUnit; // #业管单位
    private String cnName; // #中文名称
    private String enName; // #英文名称
    private String enAbbreviation; // #英文缩写
    private String oldName; // #曾用名
    private String aka1; // #别名1
    private String aka2; // #别名2
    private String aka3; // #别名3
    private String continent; // #所属洲
    private String region; // #所属区域
    private String indexed; // #是否索引
    private String delFlag; // #删除标记 1.正常 2.已删除
    private String riskLevel; // #风险等级
    private String iso2countryCode; // #ISO2国家代码
    private String iso3countryCode; // #ISO3国家代码
    private String status; // #状态 生效: Y / 失效: N
    private String remark; // #备注
    private String maintenanceReasons; // #维护原因
    private String controlRequirements; // #管控要求
    private String maintenanceState; // #维护状态 1.正式项目 2.编辑中 3.待审批 4.已删除
    private String startDate; // #添加日期
    private String expiringDate; // #失效日期
    private String uploadTime; // #上传时间
}
