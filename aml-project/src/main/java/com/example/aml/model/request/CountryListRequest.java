package com.example.aml.model.request;

import lombok.Data;

@Data
public class CountryListRequest {
    private int pageNo;
    private int pageSize;
    private String id;
    private String countryName;
    private String riskLevel;
    private String isoCode;
}
