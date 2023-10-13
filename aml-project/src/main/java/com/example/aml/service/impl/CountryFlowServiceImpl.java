package com.example.aml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.mapper.CountryFlowMapper;
import com.example.aml.model.country.FilterCountryRecordFlow;
import com.example.aml.model.request.CountryListRequest;
import com.example.aml.service.CountryFlowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryFlowServiceImpl extends ServiceImpl<CountryFlowMapper, FilterCountryRecordFlow> implements CountryFlowService {
    @Autowired
    CountryFlowMapper countryFlowMapper;

    @Override
    public Page<FilterCountryRecordFlow> findAll(CountryListRequest request) {
        try {
            Page<FilterCountryRecordFlow> page = new Page<>(request.getPageNo(), request.getPageSize());
            QueryWrapper<FilterCountryRecordFlow> condition = buildQueryCondition(request);
            condition.orderBy(true, true, "en_name");
            return countryFlowMapper.selectPage(page, condition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<FilterCountryRecordFlow> queryAuthorizedList(CountryListRequest request) {
        try {
            Page<FilterCountryRecordFlow> page = new Page<>(request.getPageNo(), request.getPageSize());
            QueryWrapper<FilterCountryRecordFlow> condition = buildQueryCondition(request);
            condition.and(wrapper -> wrapper.eq("maintenance_state", "3"));
            condition.orderBy(true, true, "en_name");
            return countryFlowMapper.selectPage(page, condition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private QueryWrapper<FilterCountryRecordFlow> buildQueryCondition(CountryListRequest request) {
        QueryWrapper<FilterCountryRecordFlow> condition = new QueryWrapper<>();
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
