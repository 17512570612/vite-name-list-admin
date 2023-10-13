package com.example.aml.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.common.Response;
import com.example.aml.model.country.FilterCountryRecord;
import com.example.aml.model.country.FilterCountryRecordFlow;
import com.example.aml.model.request.CountryListRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface CountryFlowService extends IService<FilterCountryRecordFlow>{
    Page<FilterCountryRecordFlow> findAll(CountryListRequest request);
    Page<FilterCountryRecordFlow> queryAuthorizedList(CountryListRequest request);
}
