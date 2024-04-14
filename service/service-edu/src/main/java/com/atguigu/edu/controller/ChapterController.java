package com.atguigu.edu.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.ChapterVO;
import com.atguigu.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @GetMapping("{id}")
    @ApiOperation("查询章节和小节")
    public R getChapterTree(@PathVariable String id) {
        Collection<ChapterVO> chapterTree = chapterService.getTree(id);
        return R.ok().data("items", chapterTree);
    }

    @PostMapping
    @ApiModelProperty("添加章节")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok().data("items", chapterService.getTree(chapter.getCourseId()));
    }

    @PutMapping
    @ApiModelProperty("更新章节")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok().data("items", chapterService.getTree(chapter.getCourseId()));
    }

    @DeleteMapping("{id}/{courseId}")
    @ApiModelProperty("删除章节及所属课时")
    public R deleteChapterVideo (@PathVariable String id, @PathVariable String courseId) {
        chapterService.deleteChapterVideo(id);
        return R.ok().data("items", chapterService.getTree(courseId));
    }
}

