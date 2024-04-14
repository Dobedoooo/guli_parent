package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.entity.vo.CrmBannerQuery;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.response.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author dobedoo
 * @since 2024-04-08
 */
@Api("Banner管理")
@RestController
@CrossOrigin
@RequestMapping("/crm")
public class CrmBannerController {
    private final CrmBannerService crmBannerService;

    @Autowired
    public CrmBannerController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    @ApiOperation("分页查询Banner")
    @PostMapping("{page}/{limit}")
    public R bannerPage(@PathVariable long page, @PathVariable long limit,
                        @RequestBody(required = false) CrmBannerQuery query) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.pageQuery(bannerPage, query);
        List<CrmBanner> bannerList = bannerPage.getRecords();
        long total = bannerPage.getTotal();
        return R.ok().data("items", bannerList).data("total", total);
    }

    @ApiOperation("获取一个Banner")
    @GetMapping("getBanner/{id}")
    public R addBanner(@PathVariable String id) {
        return R.ok();
    }

    @ApiOperation("添加Banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody MultipartFile file) {
        return R.ok();
    }

    @ApiOperation("修改Banner信息")
    @PutMapping("putBanner")
    public R putBanner(@RequestBody CrmBanner banner) {
        return R.ok();
    }

    @ApiOperation("删除Banner")
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        return R.ok();
    }
}

