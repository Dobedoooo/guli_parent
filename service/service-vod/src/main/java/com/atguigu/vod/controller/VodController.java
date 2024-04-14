package com.atguigu.vod.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vod/video")
@Api(description = "vod")
public class VodController {
    private final VodService vodService;

    @Autowired
    public VodController(VodService vodService) {
        this.vodService = vodService;
    }

    /**
     * 上传视频到阿里云
     */
    @PostMapping("uploadAliVideo")
    @ApiOperation("上传视频到阿里云")
    public R uploadAliVideo(@RequestBody MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 根据ID删除视频
     * @param id 视频ID
     * @return R
     */
    @DeleteMapping("deleteVideo/{id}")
    @ApiOperation("根据视频ID删除一个阿里云视频")
    public R deleteVideoById(@PathVariable String id) {
        vodService.deleteVideoById(id);
        return R.ok();
    }

    /**
     * 批量删除阿里云视频
     * @param idList id列表
     * @return R
     */
    @DeleteMapping("batchDeleteVideo")
    @ApiOperation("批量删除阿里云视频")
    public R batchDeleteVideo(@RequestParam List<String> idList) {
        vodService.batchDelete(idList);
        return R.ok();
    }
}
