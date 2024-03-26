package com.atguigu.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_course")
public class Course {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
}
