package com.example.aml.model.request;

import lombok.Data;

@Data
public class DictListRequest {
    private int pageNo;
    private int pageSize;
    private String id;
    private String code;
    private String value;
}
