package com.jydp.quartz;

import com.iqmkj.utils.DateUtil;
import com.jydp.service.IHomePageRedisService;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * web端首页数据 定时从数据库拉取到redis
 * @author yk
 */
@Component
public class HomePageTimer {

    /** web端首页 */
    @Autowired
    private IHomePageRedisService homePageRedisService;

    /** 从数据库查询所有首页数据存储到redis中(除币种行情外) (每30分钟执行一次) */
    @Scheduled(cron="0 */30 * * * ?")
    public void getHomePageData(){
        homePageRedisService.getHomePageData();
    }

    /** 从数据库查询所有币种行情信息存储到redis中 (每10秒执行一次)*/
    @Scheduled(cron="0/10 * *  * * ? ")
    public void getCurrencyMarketData(){
        homePageRedisService.getCurrencyMarketData();
    }

}
