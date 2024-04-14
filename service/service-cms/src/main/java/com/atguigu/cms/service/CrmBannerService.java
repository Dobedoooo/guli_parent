package com.atguigu.cms.service;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.entity.vo.CrmBannerQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author dobedoo
 * @since 2024-04-08
 */
public interface CrmBannerService extends IService<CrmBanner> {

    void pageQuery(Page<CrmBanner> bannerPage, CrmBannerQuery query);

    List<CrmBanner> selectIndex();
}
