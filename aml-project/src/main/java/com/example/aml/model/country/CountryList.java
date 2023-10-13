package com.example.aml.model.country;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("COUNTRY_LIST")
public class CountryList {
    private String id;
    private String enFullName;
    private String enShortName;
    private String cnName;
    private String iso2countrycode;
    private String iso3countrycode;
    private String originalName;
    private String Aka1;
    private String Aka2;
    private String Aka3;
    private String continent;
    private String area;
    private String riskScore;
    private String remark;

}
