package com.example.aml.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aml.model.country.FilterCountryRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CountryMapper extends BaseMapper<FilterCountryRecord> {
}
