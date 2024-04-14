package com.atguigu.edu.client;

import com.atguigu.commonutils.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-oss")
@Component
public interface OssClient {
    @DeleteMapping("/oss/delete")
    R delete(@RequestParam String path);
}
