package com.atguigu.oss.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

@Api(description = "aliyun文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss")
public class FileUploadController {
    @Autowired
    FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String uplaodUrl = fileService.upload(file);
        return R.ok().message("文件上传成功").data("url", uplaodUrl);
    }

}
