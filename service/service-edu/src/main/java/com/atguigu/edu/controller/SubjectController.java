package com.atguigu.edu.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.vo.Subject;
import com.atguigu.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    public R page() {
        return R.ok().data("tree" ,subjectService.getTree());
    }
}

