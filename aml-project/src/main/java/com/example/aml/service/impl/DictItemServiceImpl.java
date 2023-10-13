package com.example.aml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.mapper.DictItemMapper;
import com.example.aml.model.system.DictItem;
import com.example.aml.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {
    @Autowired
    DictItemMapper dictItemMapper;
    public List<DictItem> findAll(String relatedId) {
        QueryWrapper<DictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id",relatedId);
        queryWrapper.orderBy(true, true, "sort");
        List<DictItem> dictItems = dictItemMapper.selectList(queryWrapper);
        return dictItems;
    }
}
