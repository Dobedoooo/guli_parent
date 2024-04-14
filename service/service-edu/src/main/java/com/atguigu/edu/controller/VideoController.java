package com.atguigu.edu.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.VideoVO;
import com.atguigu.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@CrossOrigin
@Api(description = "小节管理")
@RestController
@RequestMapping("/edu/video")
public class VideoController {
    @Autowired
    VideoService videoService;

    @GetMapping("{id}")
    @ApiOperation("根据ID查询小节")
    public R getVideoById(@PathVariable String id) {
        return R.ok().data("item", videoService.getById(id));
    }

    @PostMapping
    @ApiOperation("添加小节")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);
        return R.ok().data("items",
                videoService.getVideoListByChapterId(video.getChapterId()));
    }

    @PutMapping
    @ApiOperation("修改小节")
    public R updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok().data("items",
                videoService.getVideoListByChapterId(video.getChapterId()));
    }

    @DeleteMapping("{id}/{chapterId}")
    @ApiOperation("删除小节")
    public R deleteVideo(@PathVariable String id, @PathVariable String chapterId) {
        videoService.deleteVideoByVideoId(id);
        return R.ok().data("items",
                videoService.getVideoListByChapterId(chapterId));
    }

}

