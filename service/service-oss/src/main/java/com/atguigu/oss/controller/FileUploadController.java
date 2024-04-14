package com.atguigu.oss.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

@Api(description = "aliyun文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss")
public class FileUploadController {
    @Autowired
    FileService fileService;

    /**
     *
     * @param file 文件
     * @return R
     */
    @ApiOperation(value = "头像、课程封面上传")
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String uplaodUrl = fileService.upload(file);
        return R.ok().message("文件上传成功").data("url", uplaodUrl);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/delete")
    public R delete(@RequestParam String path) {
        fileService.delete(path);
        return R.ok().message("文件删除成功");
    }

}
