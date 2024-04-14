package com.atguigu.edu.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubject {

    @ExcelProperty(index = 0)
    private String topLevel;

    @ExcelProperty(index = 1)
    private String secondaryLevel;
}
