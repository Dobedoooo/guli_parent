package com.atguigu.edu.service;

import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-26
 */
public interface ChapterService extends IService<Chapter> {

    Collection<ChapterVO> getTree(String id);

    boolean deleteChapterVideo(String id);

    void deleteByCourseId(String id);
}
