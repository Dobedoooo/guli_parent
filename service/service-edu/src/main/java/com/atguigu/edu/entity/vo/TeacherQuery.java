package com.atguigu.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
public class TeacherQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer level;
    private String start;
    private String end;
}
