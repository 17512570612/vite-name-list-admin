package com.example.aml.model.request;

import lombok.Data;

@Data
public class MenuRequest {
    private int pageNo;
    private int pageSize;
    private String id;
}
