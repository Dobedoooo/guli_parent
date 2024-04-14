package com.atguigu.oss.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传至阿里云
     * @return
     */
    String upload(MultipartFile file);

    void delete(String path);
}
