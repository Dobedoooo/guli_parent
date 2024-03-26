package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.commonutils.response.ResultCode;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {
        String endpoiont = ConstantPropertiesUtil.ENDPOINT;
        String keyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String keySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        String uploadUrl = null;


        try {
            OSSClient ossClient = new OSSClient(endpoiont, keyId, keySecret);
            if(!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取文件上传流
            InputStream inputStream = file.getInputStream();

            // 构建日期路径: avatar/2024/03/21/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");

            // 文件名 uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = fileHost + "/" + filePath + "/" + newName;

            // 上传至aliyun
            ossClient.putObject(bucketName, fileUrl, inputStream);
            ossClient.shutdown();

            uploadUrl = "http://" + bucketName + "." + endpoiont + "/" + fileUrl;

        } catch (IOException e) {
            throw new GuliException(ResultCode.FILE_UPLOAD_ERROR);
        }
        return uploadUrl;
    }
}
