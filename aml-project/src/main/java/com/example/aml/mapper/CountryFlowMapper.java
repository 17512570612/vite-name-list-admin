package com.example.aml.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aml.model.country.FilterCountryRecordFlow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CountryFlowMapper  extends BaseMapper<FilterCountryRecordFlow> {
}
