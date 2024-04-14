package com.atguigu.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable {

    public static final long serialVersionUID = 1l;

    private String title;

    private String status;
}
