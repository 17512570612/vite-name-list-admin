package com.example.aml.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aml.mapper.CountryListMapper;
import com.example.aml.model.country.CountryList;
import com.example.aml.service.CountryListService;
import org.springframework.stereotype.Service;

@Service
public class CountryListServiceImpl  extends ServiceImpl<CountryListMapper, CountryList> implements CountryListService {
}
