package com.atguigu.edu.service.impl;

import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.edu.client.OssClient;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVO;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseDescriptionService;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.VideoService;
import com.atguigu.edu.util.VideoDeleteBy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final CourseDescriptionService courseDescriptionService;
    private final ChapterService chapterService;
    private final VideoService videoService;
    private final OssClient ossClient;

    @Autowired
    public CourseServiceImpl(CourseDescriptionService courseDescriptionService,
                             ChapterService chapterService,
                             VideoService videoService,
                             OssClient ossClient) {
        this.courseDescriptionService = courseDescriptionService;
        this.chapterService = chapterService;
        this.videoService = videoService;
        this.ossClient = ossClient;
    }

    /**
     * 添加课程信息
     * @param courseInfoVo 课程信息
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 向课程表添加信息 CourseInfoVo -> Course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        // 2. 向课程简介表添加
        CourseDescription courseDescription = new CourseDescription();
        // 如果穿过来的id是空，就添加，不空就更新
        if(StringUtils.isEmpty(courseInfoVo.getId())) {
            int rowNum = baseMapper.insert(course);
            if(rowNum == 0) throw new GuliException(20001, "添加课程信息失败");

            courseDescription.setDescription(courseInfoVo.getDescription()).setId(course.getId());
            courseDescriptionService.save(courseDescription);
        } else {
            courseDescription.setDescription(courseInfoVo.getDescription()).setId(course.getId());
            baseMapper.updateById(course);
            courseDescriptionService.updateById(courseDescription);
        }
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        //
        Course course = this.getById(id);
        if(course == null) throw new GuliException(20001, "没有此课程 id: " + id);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public CoursePublishVO getCoursePublish(String courseId) {
        return baseMapper.getPublishInfo(courseId);
    }

    // 分页查询课程
    @Override
    public void pageQuery(Page<Course> coursePage, CourseQuery courseQuery) {
        if(courseQuery == null) {
            this.page(coursePage, null);
            return;
        }

        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if(!StringUtils.isEmpty(title)) {
            courseQueryWrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(status)) {
            courseQueryWrapper.eq("status", status);
        }
        this.page(coursePage, courseQueryWrapper);
    }

    /**
     * 根据课程id删除课程、章节、课时
     * @param id
     * @return
     */
    @Override
    public boolean deleteCourse(String id) {
        // 删除小节
        videoService.deleteBy(VideoDeleteBy.COURSE_ID, id);
        // 删除章节
        chapterService.deleteByCourseId(id);
        // 删除课程描述
        courseDescriptionService.removeById(id);
        // 删除课程封面
        String path = baseMapper.selectById(id).getCover();
        ossClient.delete(path);
        // 删除课程
        return baseMapper.deleteById(id) > 0;
    }

    /**
     * 查询前8条热门课程
     * @return 8
     */
    @Override
    @Cacheable(value = "indexCourse", key = "'getIndexCourse-yym'")
    public List<Course> getIndexCourse() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        return baseMapper.selectList(courseQueryWrapper);
    }
}
