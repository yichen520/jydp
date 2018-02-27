package com.jydp.quartz;

import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * web端首页数据 定时从数据库拉取到redis
 * @author yk
 */
@Component
public class HomePageTimer {

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** web端首页 */
    @Autowired
    private IHomePageService homePageService;
}
