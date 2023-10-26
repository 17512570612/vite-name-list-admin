package com.example.aml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.mapper.CountryFlowMapper;
import com.example.aml.mapper.CountryMapper;
import com.example.aml.model.country.FilterCountryRecord;
import com.example.aml.model.country.FilterCountryRecordFlow;
import com.example.aml.model.request.CountryListRequest;
import com.example.aml.service.CountryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, FilterCountryRecord> implements CountryService {
    @Autowired
    CountryMapper countryMapper;

    @Override
    public Page<FilterCountryRecord> findAll(CountryListRequest request) {
        try {
            Page<FilterCountryRecord> page = new Page<>(request.getPageNo(), request.getPageSize());
            QueryWrapper<FilterCountryRecord> condition = buildQueryCondition(request);
            condition.orderBy(true, true, "en_name");
            return countryMapper.selectPage(page, condition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private QueryWrapper<FilterCountryRecord> buildQueryCondition(CountryListRequest request) {
        QueryWrapper<FilterCountryRecord> condition = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getRiskLevel())) {
            condition.and(wrapper -> wrapper.eq("risk_level", request.getRiskLevel()));
        }
        if (StringUtils.isNotBlank(request.getCountryName())) {
            condition.and(wrapper -> wrapper
                    .like("en_name", request.getCountryName())
                    .or()
                    .like("cn_name", request.getCountryName())
                    .or()
                    .like("en_abbreviation", request.getCountryName()));
        }
        if (StringUtils.isNotBlank(request.getIsoCode())) {
            condition.and(wrapper -> wrapper
                    .eq("iso2country_code", request.getIsoCode())
                    .or()
                    .eq("iso3country_code", request.getIsoCode()));
        }
        return condition;
    }
}
