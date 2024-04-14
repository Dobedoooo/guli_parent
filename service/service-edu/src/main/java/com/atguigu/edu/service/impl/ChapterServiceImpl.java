package com.atguigu.edu.service.impl;

import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.ChapterVO;
import com.atguigu.edu.entity.vo.VideoVO;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.VideoService;
import com.atguigu.edu.util.VideoDeleteBy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    VideoService videoService;
    VodClient vodClient;

    @Autowired
    public ChapterServiceImpl(VideoService videoService, VodClient vodClient) {
        this.videoService = videoService;
        this.vodClient = vodClient;
    }

    @Override
    public Collection<ChapterVO> getTree(String id) {
        // 1. 查出该 id 下所有章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<Chapter>();
        chapterQueryWrapper.eq("course_id", id);
        List<Chapter> chapterList = this.list(chapterQueryWrapper);
        Map<String, ChapterVO> chapterMap = chapterList.stream().map(chapter -> {
            Map<String, ChapterVO> map = new HashMap<>();
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            map.put(chapter.getId(), chapterVO);
            return map;
        }).reduce(new HashMap<>(), (m, kv) -> {
            m.putAll(kv);
            return m;
        });
        // 2. 查出该 id 下所有小节
        List<Video> videoList = videoService.list(new QueryWrapper<Video>().eq("course_id", id).orderByAsc("sort"));
        // 3. 将小节放到章节内
        videoList.forEach(v -> {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(v, videoVO);
            if(chapterMap.get(v.getChapterId()) == null) return;
            chapterMap.get(v.getChapterId())
                    .getVideoList()
                    .add(videoVO);
        });
        return chapterMap.values();
    }

    @Override
    public boolean deleteChapterVideo(String id) {
        videoService.deleteBy(VideoDeleteBy.CHAPTER_ID, id);
        return baseMapper.deleteById(id) > 0;
    }


    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        baseMapper.delete(chapterQueryWrapper);
    }
}
