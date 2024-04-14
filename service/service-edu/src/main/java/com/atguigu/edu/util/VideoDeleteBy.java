package com.atguigu.edu.util;

public enum VideoDeleteBy {
    CHAPTER_ID("chapter_id"),
    COURSE_ID("course_id");

    public final String value;

    VideoDeleteBy(String value) {
        this.value = value;
    }
}
