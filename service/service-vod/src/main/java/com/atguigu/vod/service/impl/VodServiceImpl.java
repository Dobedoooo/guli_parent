package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.ConstantPropertiesUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    public IAcsClient createIAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-shanghai",
                ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        UploadStreamRequest request;
        String title = file.getOriginalFilename().split("\\.")[0];
        try {
            request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, file.getOriginalFilename(),
                    file.getInputStream()
            );
        } catch (IOException e) { throw new GuliException(20001, "创建上传请求对象出错"); }
        UploadStreamResponse response = new UploadVideoImpl().uploadStream(request);
        if(response.isSuccess()) {
            return response.getVideoId();
        } else {
            throw new GuliException(20001, "code :" + response.getCode() + ", msg: " + response.getMessage());
        }
    }

    @Override
    public void deleteVideoById(String id) {

        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);

        try {
            createIAcsClient().getAcsResponse(request);
        } catch (ServerException e) {
            throw new GuliException(20001, "删除视频错误");
        } catch (ClientException e) {
            throw new GuliException(20001, "ErrCode:" + e.getErrCode() + ", ErrMsg:" + e.getErrMsg());
        }
    }

    @Override
    public void batchDelete(List<String> idList) {
        if(idList.isEmpty()) return;
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(String.join(",", idList));

        try {
            createIAcsClient().getAcsResponse(request);
        } catch (ServerException e) {
            throw new GuliException(20001, "删除视频错误");
        } catch (ClientException e) {
            throw new GuliException(20001, "ErrCode:" + e.getErrCode() + ", ErrMsg:" + e.getErrMsg());
        }
    }

}
