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

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** 从数据库查询所有首页数据存储到redis中(除币种行情外) */
    @Override
    public void getHomePageData() {
        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesList = homePageService.getSystemAdsHomepageList();
        String adsKey = RedisKeyConfig.HOMEPAGE_ADS;

        if (systemAdsHomepagesList != null && systemAdsHomepagesList.size() > 0) {
            redisService.addValue(adsKey,systemAdsHomepagesList);
        } else {
            redisService.addValue(adsKey,null);
        }

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeList = homePageService.getSystemNoticeList();
        String noticeKey = RedisKeyConfig.HOMEPAGE_NOTICE;

        if (systemNoticeList != null && systemNoticeList.size() > 0) {
            redisService.addValue(noticeKey,systemNoticeList);
        } else {
            redisService.addValue(noticeKey,null);
        }

        //查询热门话题列表
        List<SystemHotDO> systemHotList = homePageService.getSystemHotList();
        String hotTopicKey = RedisKeyConfig.HOMEPAGE_HOT_TOPIC;

        if (systemHotList != null && systemHotList.size() > 0) {
            redisService.addValue(hotTopicKey,systemHotList);
        } else {
            redisService.addValue(hotTopicKey,null);
        }

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = homePageService.getSystemBusinessesPartnerList();
        String partnerKey = RedisKeyConfig.HOMEPAGE_PARTNER;

        if (systemBusinessesPartnerList != null && systemBusinessesPartnerList.size() > 0) {
            redisService.addValue(partnerKey,systemBusinessesPartnerList);
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
        List<TransactionUserDealDTO> transactionUserDealList = homePageService.getTransactionCurrencyMarketList();
        String marketKey = RedisKeyConfig.HOMEPAGE_CURRENCY_MARKET;

        if (transactionUserDealList != null && transactionUserDealList.size() > 0) {
            redisService.addValue(marketKey,transactionUserDealList);
        } else {
            redisService.addValue(marketKey,null);
        }
    }
}
