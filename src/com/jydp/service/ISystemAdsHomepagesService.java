package com.jydp.service;

import com.jydp.entity.DO.system.SystemAdsHomepagesDO;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
public interface ISystemAdsHomepagesService {

    /**
     * web端用户查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    List<SystemAdsHomepagesDO> getSystemAdsHomepageslistForWeb();
}
