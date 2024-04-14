package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CoursePublishVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublishVO getPublishInfo(String courseId);
}
