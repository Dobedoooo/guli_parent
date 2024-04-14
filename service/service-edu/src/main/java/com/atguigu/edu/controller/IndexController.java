package com.atguigu.edu.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Api(description = "前台首页数据")
@RequestMapping("/edu/index")
public class IndexController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public IndexController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @ApiOperation("首页4个讲师和8个课程")
    @GetMapping
    public R getIndex() {
        // 查询前8条热门课程
        List<Course> courseList = courseService.getIndexCourse();
        // 查询前4条热门课程
        List<Teacher> teacherList = teacherService.getIndexTeacher();
        return R.ok()
                .data("teacherList", teacherList)
                .data("courseList", courseList);
    }
}
