package com.jydp.service.impl.common;

import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.service.IHomePageRedisService;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  web端首页Redis服务
 *  @author yk
 */
@Service("homePageRedisService")
public class HomePageRedisServiceImpl implements IHomePageRedisService{

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    @Autowired
    private IHomePageService homePageService;

    /** 从数据库查询所有首页数据存储到redis中(除币种行情外) */
    @Override
    public void getHomePageData() {

        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = homePageService.getSystemAdsHomepageList();
        String adsKey = RedisKeyConfig.HOMEPAGE_ADS;

        if (systemAdsHomepagesDOList != null && systemAdsHomepagesDOList.size() > 0) {
            redisService.addValue(adsKey,systemAdsHomepagesDOList);
        } else {
            redisService.addValue(adsKey,null);
        }

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeDOList = homePageService.getSystemNoticeList();
        String noticeKey = RedisKeyConfig.HOMEPAGE_NOTICE;

        if (systemNoticeDOList != null && systemNoticeDOList.size() > 0) {
            redisService.addValue(noticeKey,systemNoticeDOList);
        } else {
            redisService.addValue(noticeKey,null);
        }

        //查询热门话题列表
        List<SystemHotDO> systemHotDOList = homePageService.getSystemHotList();
        String hotTopicKey = RedisKeyConfig.HOMEPAGE_HOTTOPIC;

        if (systemHotDOList != null && systemHotDOList.size() > 0) {
            redisService.addValue(hotTopicKey,systemHotDOList);
        } else {
            redisService.addValue(hotTopicKey,null);
        }

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = homePageService.getSystemBusinessesPartnerList();
        String partnerKey = RedisKeyConfig.HOMEPAGE_PARTNER;

        if (systemBusinessesPartnerDOList != null && systemBusinessesPartnerDOList.size() > 0) {
            redisService.addValue(partnerKey,systemBusinessesPartnerDOList);
        } else {
            redisService.addValue(partnerKey,null);
        }
    }

    /**
     * 从数据库查询所有币种行情信息存储到redis中
     */
    @Override
    public void getCurrencyMarketData() {
        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealDTOList = homePageService.getTransactionCurrencyMarketList();
        String marketKey = RedisKeyConfig.HOMEPAGE_CURRENCYMARKET;

        if (transactionUserDealDTOList != null && transactionUserDealDTOList.size() > 0) {
            redisService.addValue(marketKey,transactionUserDealDTOList);
        } else {
            redisService.addValue(marketKey,null);
        }
    }
}
