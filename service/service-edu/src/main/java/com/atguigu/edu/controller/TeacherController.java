package com.atguigu.edu.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-18
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/teacher")
@Api(description = "讲师管理")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping
    public R list() {
        List<Teacher> list =  teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("分页讲师列表")
    @PostMapping("{page}/{limit}")
    public R pageList(
        @PathVariable long page,
        @PathVariable long limit,
        @RequestBody(required = false) TeacherQuery teacherQuery
    ) {

        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherService.pageQuery(teacherPage, teacherQuery);
        List<Teacher> list = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("items", list).data("total", total);
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        return teacherService.removeById(id) ? R.ok() : R.error().message("删除失败");
    }

    @ApiOperation("新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher", value = "讲师", required = true)
            @RequestBody Teacher teacher) {

        teacherService.save(teacher);
        return R.ok();
    }

    @ApiOperation("根据id查询")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {

        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation("根据id更新")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师", required = true)
            @RequestBody Teacher teacher) {

        teacher.setId(id);
        return teacherService.updateById(teacher) ? R.ok() : R.error();
    }

}

