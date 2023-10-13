package com.example.aml.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aml.model.country.FilterCountryRecord;
import com.example.aml.model.country.FilterCountryRecordFlow;
import com.example.aml.model.request.CountryListRequest;

public interface CountryService extends IService<FilterCountryRecord> {
    Page<FilterCountryRecord> findAll(CountryListRequest request);
}
