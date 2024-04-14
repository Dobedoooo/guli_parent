package com.atguigu.edu.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVO;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }



    @ApiOperation("保存课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("id", courseId);
    }

    @ApiOperation("查询课程信息")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(id);
        return R.ok().data("course", courseInfoVo);
    }

    @ApiOperation("查询课程发布信息")
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublish(@PathVariable String courseId) {
        CoursePublishVO coursePublishInfo = courseService.getCoursePublish(courseId);
        return R.ok().data("item", coursePublishInfo);
    }

    @ApiOperation("发布课程")
    @GetMapping("publish/{courseId}")
    public R publish(@PathVariable String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(Course.COURSE_NORMAL);
        boolean flag = courseService.updateById(course);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation("查询课程列表")
    @PostMapping("{page}/{limit}")
    public R getList(@PathVariable long page, @PathVariable long limit,
                     @RequestBody(required = false) CourseQuery courseQuery) {
        Page<Course> coursePage = new Page<>(page, limit);
        courseService.pageQuery(coursePage, courseQuery);
        List<Course> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();
        return R.ok().data("items", courseList).data("total", total);
    }

    @ApiOperation("根据课程ID删除课程、章节、课时")
    @DeleteMapping("{id}")
    public R getList(@PathVariable String id) {
        return courseService.deleteCourse(id) ? R.ok() : R.error();
    }
}

