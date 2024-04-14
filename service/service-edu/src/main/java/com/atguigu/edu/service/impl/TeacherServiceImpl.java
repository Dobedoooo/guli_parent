package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.mapper.TeacherMapper;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-03-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public void pageQuery(Page<Teacher> page, TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        qw.orderByAsc("sort");

        if(teacherQuery == null) {
            baseMapper.selectPage(page, qw);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String start = teacherQuery.getStart();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)) {
            qw.like("name", name);
        }
        if(!StringUtils.isEmpty(level)) {
            qw.eq("level", level);
        }
        if(!StringUtils.isEmpty(start)) {
            qw.ge("gmt_create", start);
        }
        if(!StringUtils.isEmpty(end)) {
            qw.le("gmt_create", end);
        }

        baseMapper.selectPage(page, qw);
    }

    @Override
    public boolean removeById(Serializable id) {
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    /**
     * 查询前4条热门课程
     * @return 4
     */
    @Override
    @Cacheable(value = "indexTeacher", key = "'getIndexCourse-yym'")
    public List<Teacher> getIndexTeacher() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        return baseMapper.selectList(teacherQueryWrapper);
    }
}
