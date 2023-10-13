package com.example.aml.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.model.system.DictItem;

import java.util.List;

public interface DictItemService extends IService<DictItem> {
    List<DictItem> findAll(String relatedId);
}
