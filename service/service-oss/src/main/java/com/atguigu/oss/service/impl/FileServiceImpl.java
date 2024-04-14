package com.atguigu.oss.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.commonutils.response.ResultCode;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String upload(MultipartFile file) {

        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String keyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String keySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        String uploadUrl = null;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
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
            assert original != null;
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = fileHost + "/" + filePath + "/" + newName;

            // 上传至aliyun
            ossClient.putObject(bucketName, fileUrl, inputStream);
            ossClient.shutdown();

            uploadUrl = "http://" + bucketName + "." + endpoint + "/" + fileUrl;

        } catch (IOException e) {
            throw new GuliException(ResultCode.FILE_UPLOAD_ERROR);
        }
        return uploadUrl;
    }

    @Override
    public void delete(String path) {
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String keyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String keySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        // path http://guli-file-wanglei.oss-cn-beijing.aliyuncs.com/avatar/2024/03/27/2c358a98-eb96-4053-995b-e378bf1d17bb.jfif
        String fileName = path.substring(path.lastIndexOf(fileHost));

        OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        try {
            ossClient.deleteObject(bucketName, fileName);
        } catch (OSSException oe) {
            throw new GuliException(20001, "Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason."
                    + " Error Message:" + oe.getMessage());
        } catch (ClientException ce) {
            throw new GuliException(20001, "Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network." + " Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
