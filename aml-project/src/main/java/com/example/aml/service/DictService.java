package com.example.aml.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.model.request.DictListRequest;
import com.example.aml.model.system.Dict;

public interface DictService extends IService<Dict> {
    Page<Dict> findAll(DictListRequest request);
}
