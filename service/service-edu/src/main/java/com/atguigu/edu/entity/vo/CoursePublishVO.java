package com.atguigu.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("课程发布信息")
@Data
public class CoursePublishVO implements Serializable {

    public static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;

    private String status;
}
