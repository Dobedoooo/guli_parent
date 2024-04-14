package com.atguigu.edu.service;

import com.atguigu.edu.entity.vo.TopSubject;
import com.atguigu.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface SubjectService extends IService<Subject> {
    void batchImport(MultipartFile file, SubjectService subjectService);
    Collection<TopSubject> getTree();

    List<Subject> getAllTop();

    List<Subject> getSecond(String id);
}
