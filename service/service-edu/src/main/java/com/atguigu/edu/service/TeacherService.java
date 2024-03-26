package com.atguigu.edu.service;

import com.atguigu.edu.entity.vo.Teacher;
import com.atguigu.edu.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-18
 */
public interface TeacherService extends IService<Teacher> {
    void pageQuery(Page<Teacher> page, TeacherQuery teacherQuery);
    boolean removeById(Serializable id);
}
