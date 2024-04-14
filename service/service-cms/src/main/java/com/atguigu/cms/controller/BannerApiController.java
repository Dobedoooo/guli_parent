package com.atguigu.cms.controller;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@RestController
@CrossOrigin
@Api(description = "网站首页Banner列表")
@RequestMapping("/cms/banner")
public class BannerApiController {

    private final CrmBannerService crmBannerService;

    @Autowired
    public BannerApiController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    @ApiOperation("获取首页banner")
    @GetMapping
    public R index() {
        List<CrmBanner> list = crmBannerService.selectIndex();
        return R.ok().data("items", list);
    }
}
