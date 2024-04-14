package com.atguigu.edu.client;

import com.atguigu.commonutils.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/vod/video/deleteVideo/{id}")
    R deleteVideoById(@PathVariable("id") String id);

    @DeleteMapping("/vod/video/batchDeleteVideo")
    R batchDeleteVideo(@RequestParam List<String> idList);
}
