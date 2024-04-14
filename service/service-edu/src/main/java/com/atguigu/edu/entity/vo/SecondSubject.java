package com.atguigu.edu.entity.vo;

import lombok.Data;

@Data
public class SecondSubject {
    private String id;
    private String label;

    public SecondSubject(String id, String title) {
        this.id = id;
        this.label = title;
    }
}
