package com.example.aml.model.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelError {
    private int rowNum;
    private int colNum;
    private String fieldName;
    private String errorMessage;
}
