package com.atguigu.edu.service;

import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.VideoVO;
import com.atguigu.edu.util.VideoDeleteBy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
public interface VideoService extends IService<Video> {
    List<Video> getVideoListByChapterId(String chapterId);

    void deleteByCourseId(String id);

    boolean deleteVideoByVideoId(String id);

    void deleteBy(VideoDeleteBy column, String id);
}
