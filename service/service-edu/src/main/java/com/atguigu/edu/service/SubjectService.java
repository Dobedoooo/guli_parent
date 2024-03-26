package com.atguigu.edu.service;

import com.atguigu.edu.entity.subject.TopSubject;
import com.atguigu.edu.entity.vo.Subject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface SubjectService extends IService<Subject> {
    void batchImport(MultipartFile file, SubjectService subjectService);
    Collection<TopSubject> getTree();
}
