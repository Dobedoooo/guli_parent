package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.edu.entity.vo.ExcelSubject;
import com.atguigu.edu.entity.vo.SecondSubject;
import com.atguigu.edu.entity.vo.TopSubject;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.listener.SubjectExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Override
    public void batchImport(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubject.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            throw new GuliException(20002, "添加课程失败");
        }
    }

    @Override
    public Collection<TopSubject> getTree() {
        List<Subject> topSubjectList = this.list(new QueryWrapper<Subject>().eq("parent_id", "0"));
        List<Subject> secSubjectList = this.list(new QueryWrapper<Subject>().ne("parent_id", "0"));
        Map<String, TopSubject> topMap = topSubjectList.stream().map(s -> {
            Map<String, TopSubject> map = new HashMap<>();
            map.put(s.getId(), new TopSubject(s.getId(), s.getTitle()));
            return map;
        }).reduce(new HashMap<>(), (m, kv) -> {
            m.putAll(kv);
            return m;
        });

        secSubjectList.forEach(s -> {
            topMap.get(s.getParentId()).getChildren().add(new SecondSubject(s.getId(), s.getTitle()));
        });
        return topMap.values();
    }

    @Override
    public List<Subject> getAllTop() {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("parent_id", "0");
        return this.list(subjectQueryWrapper);
    }

    @Override
    public List<Subject> getSecond(String id) {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("parent_id", id);
        return this.list(subjectQueryWrapper);
    }
}
