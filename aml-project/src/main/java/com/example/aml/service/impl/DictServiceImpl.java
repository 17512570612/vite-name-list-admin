package com.example.aml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.mapper.DictMapper;
import com.example.aml.model.request.DictListRequest;
import com.example.aml.model.system.Dict;
import com.example.aml.model.system.DictItem;
import com.example.aml.service.DictItemService;
import com.example.aml.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Autowired
    DictMapper dictMapper;
    @Override
    public Page<Dict> findAll(DictListRequest request) {
        try {
            Page<Dict> page = new Page<>(request.getPageNo(), request.getPageSize());
            QueryWrapper<Dict> condition = buildQueryCondition(request);
            return dictMapper.selectPage(page, condition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private QueryWrapper<Dict> buildQueryCondition(DictListRequest request) {
        QueryWrapper<Dict> condition = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getCode())) {
            condition.and(wrapper -> wrapper.like("code", request.getCode()));
        }
        if (StringUtils.isNotBlank(request.getValue())) {
            condition.and(wrapper -> wrapper.like("value", request.getValue()));
        }
        return condition;
    }
}
