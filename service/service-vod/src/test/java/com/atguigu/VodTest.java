package com.atguigu;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class VodTest {

    public static final String ALIBABA_CLOUD_ACCESS_KEY_ID = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
    public static final String ALIBABA_CLOUD_ACCESS_KEY_SECRET = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");

    public static DefaultAcsClient initVodClient() throws ClientException {
        String regionId = "cn-shanghai";
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId,
                System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        return new DefaultAcsClient(defaultProfile);
    }

    /**
     * 根据视频id获取播放地址
     */
    @Test
    public void getVideoAddress() throws ClientException {
        IAcsClient client = initVodClient();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("503d54daee8c71eeaf534531958c0102");

        try {
            GetPlayInfoResponse response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> list = response.getPlayInfoList();
            System.out.println("视频名称: " + response.getVideoBase().getTitle());
            for (GetPlayInfoResponse.PlayInfo info: list) {
                System.out.println("播放地址: " + info.getPlayURL());
            }
        } catch (ServerException se) {
            se.printStackTrace();
        } catch (ClientException ce) {
            System.out.println("ErrCode:" + ce.getErrCode());
            System.out.println("ErrMsg:" + ce.getErrMsg());
            System.out.println("RequestId:" + ce.getRequestId());
        }
    }
    /**
     * 根据视频id获取播放凭证
     */
    @Test
    public void getVideoAuth() throws ClientException {
        IAcsClient client = initVodClient();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("503d54daee8c71eeaf534531958c0102");

        try {
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            System.out.println("视频元信息: " + response.getVideoMeta().getTitle());
            System.out.println("播放地址: " + response.getPlayAuth());

        } catch (ServerException se) {
            se.printStackTrace();
        } catch (ClientException ce) {
            System.out.println("ErrCode:" + ce.getErrCode());
            System.out.println("ErrMsg:" + ce.getErrMsg());
            System.out.println("RequestId:" + ce.getRequestId());
        }
    }

    /**
     * 本地文件上传
     */
    @Test
    public void uploadVideoLocal() {
        String title = "cat";
        String fileName = "C:/Users/dobedoo/Desktop/1665669595.mp4";
        UploadVideoRequest request = new UploadVideoRequest(ALIBABA_CLOUD_ACCESS_KEY_ID,
                ALIBABA_CLOUD_ACCESS_KEY_SECRET, title, fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if(response.isSuccess()) {
            System.out.println(response.getVideoId());
        } else {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    @Test
    public void testSplit() {
        String a = "asd.mp4".split("\\.")[0];
        System.out.println(a);
    }
}
