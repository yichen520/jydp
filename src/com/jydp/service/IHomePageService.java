package com.jydp.service;

import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import java.util.List;

/**
 * web端首页
 */
public interface IHomePageService {

    /**
     * 查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    List<SystemNoticeDO> getSystemNoticeList();

    /**
     * 查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    List<SystemAdsHomepagesDO> getSystemAdsHomepageList();

    /**
     * 查询热门话题列表
     * @return 返回热门话题列表；查询失败：返回null
     */
    List<SystemHotDO> getSystemHotList();

    /**
     * 查询合作商家列表
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerList();
}
