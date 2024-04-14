package com.atguigu.cms.service.impl;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.entity.vo.CrmBannerQuery;
import com.atguigu.cms.mapper.CrmBannerMapper;
import com.atguigu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author dobedoo
 * @since 2024-04-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Override
    @Cacheable(value = "banner", key = "'selectIndex-yym'")
    public List<CrmBanner> selectIndex() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByAsc("sort");
        crmBannerQueryWrapper.select("title", "image_url", "link_url");
        crmBannerQueryWrapper.last("limit 2");
        return baseMapper.selectList(crmBannerQueryWrapper);
    }

    @Override
    public void pageQuery(Page<CrmBanner> bannerPage, CrmBannerQuery query) {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByAsc("sort");

        if(query == null) {
            baseMapper.selectPage(bannerPage, null);
            return;
        }


    }
}
