package com.atguigu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.edu.entity.vo.ExcelSubject;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubject> {
    private SubjectService subjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 一行一行读取 excel
    @Override
    public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
        if(excelSubject == null) throw new GuliException(20001, "添加失败");
        Subject topSubject = getTopSubject(subjectService, excelSubject.getTopLevel());
        if(topSubject == null) {    // 如果没有，就添加
            topSubject = new Subject();
            topSubject.setTitle(excelSubject.getTopLevel());
            topSubject.setParentId("0");
            subjectService.save(topSubject);
        }

        String pid = topSubject.getId();

        Subject secSubject = getSecSubject(subjectService, excelSubject.getSecondaryLevel(), pid);
        if(secSubject == null) {
            secSubject = new Subject();
            secSubject.setTitle(excelSubject.getSecondaryLevel());
            secSubject.setParentId(pid);
            subjectService.save(secSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

    private Subject getTopSubject(SubjectService subjectService, String subjectName) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", subjectName);
        queryWrapper.eq("parent_id", "0");
        return subjectService.getOne(queryWrapper);
    }

    private Subject getSecSubject(SubjectService subjectService, String subjectName, String id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", subjectName);
        queryWrapper.eq("parent_id", id);
        return subjectService.getOne(queryWrapper);
    }
}
