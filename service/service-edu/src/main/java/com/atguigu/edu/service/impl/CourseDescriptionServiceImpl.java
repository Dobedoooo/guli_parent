package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.mapper.CourseDescriptionMapper;
import com.atguigu.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {
}
