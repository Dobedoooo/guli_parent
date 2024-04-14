package com.atguigu.edu.service;

import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

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

    List<Teacher> getIndexTeacher();
}
