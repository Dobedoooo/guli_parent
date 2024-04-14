package com.atguigu.edu.service.impl;

import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.commonutils.response.R;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.VideoService;
import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.util.VideoDeleteBy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    // public static final String

    private final VodClient vodClient;

    @Autowired
    public VideoServiceImpl(VodClient vodClient) {
        this.vodClient = vodClient;
    }

    @Override
    public List<Video> getVideoListByChapterId(String chapterId) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        videoQueryWrapper.orderByAsc("sort");
        return this.list(videoQueryWrapper);
    }

    // TODO 删除对应视频文件
    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        baseMapper.delete(videoQueryWrapper);
    }

    /**
     * 删除章节或课程下的视频
     * @param column 指定删除章节或课程
     * @param id 章节或课程id
     */
    @Override
    public void deleteBy(VideoDeleteBy column, String id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq(column.value, id);
        videoQueryWrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(videoQueryWrapper);
        // 查询出的小节不为空时，删除小节和云端视频
        if(!videoList.isEmpty()) {
            List<String> idList = videoList.stream()
                    .map(Video::getVideoSourceId)
                    .filter(vid -> !StringUtils.isEmpty(vid))
                    .collect(Collectors.toList());
            baseMapper.delete(videoQueryWrapper);
            vodClient.batchDeleteVideo(idList);
        }
    }

    /**
     * 根据小节id删除小节及云端视频
     * @param id 小节ID
     */
    @Override
    public boolean deleteVideoByVideoId(String id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.deleteVideoById(videoSourceId);
            if(!r.getSuccess()) {
                throw new GuliException(20001, "熔断器中断");
            }
        }
        return baseMapper.deleteById(id) > 0;
    }

    public static void main(String[] args) {
        List<Video> videoList = Arrays.asList(new Video().setVideoSourceId(""), new Video().setVideoSourceId(""));
        List<String> idList = videoList.stream()
                .map(Video::getVideoSourceId)
                .filter(v -> !StringUtils.isEmpty(v))
                .collect(Collectors.toList());
        System.out.println(idList);
        System.out.println(idList.isEmpty());
    }
}
