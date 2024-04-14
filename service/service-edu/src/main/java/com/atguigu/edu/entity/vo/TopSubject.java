package com.atguigu.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TopSubject {
    private String id;
    private String label;
    private List<SecondSubject> children = new ArrayList<>();

    public TopSubject(String id, String title) {
        this.id = id;
        this.label = title;
    }
}
