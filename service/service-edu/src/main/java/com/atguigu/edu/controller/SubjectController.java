package com.atguigu.edu.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/edu/subject")
@Api(description = "课程分类管理")
@CrossOrigin
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @ApiOperation("Excel批量导入")
    @PostMapping("addSubject")
    public R addSubjects(MultipartFile file) {
        subjectService.batchImport(file, subjectService);
        return R.ok();
    }

    @ApiOperation("树形结构展示")
    @GetMapping
    public R tree() {
        return R.ok().data("tree" ,subjectService.getTree());
    }

    @ApiOperation("所有一级分类")
    @GetMapping("allTop")
    public R getAllTop() {
        return R.ok().data("items", subjectService.getAllTop());
    }

    @ApiOperation("查询一级分类下的课程")
    @GetMapping("secondSubject/{id}")
    public R getSecond(@PathVariable String id) {
        return R.ok().data("items", subjectService.getSecond(id));
    }
}

