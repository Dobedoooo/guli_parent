package com.atguigu.edu.client;

import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.commonutils.response.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 出错才会执行
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideoById(String id) {
        return R.error().message("服务器错误");
    }

    @Override
    public R batchDeleteVideo(List<String> idList) {
        return R.error().message("服务器错误");
    }
}
