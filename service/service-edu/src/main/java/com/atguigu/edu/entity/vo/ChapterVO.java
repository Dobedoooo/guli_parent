package com.atguigu.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {

    private String id;

    private String title;

    private List<VideoVO> videoList = new ArrayList<>();

    private Integer sort;

}
