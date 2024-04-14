package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVO;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String id);

    CoursePublishVO getCoursePublish(String courseId);

    void pageQuery(Page<Course> coursePage, CourseQuery courseQuery);

    boolean deleteCourse(String id);

    List<Course> getIndexCourse();
}
