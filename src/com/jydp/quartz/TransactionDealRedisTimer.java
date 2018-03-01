package com.jydp.quartz;

import com.jydp.service.ITransactionDealRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 交易中心成交记录 定时从数据库拉取到redis
 * @author fk
 */
@Component
public class TransactionDealRedisTimer {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 成交记录  每5秒刷新一次 */
    @Scheduled(cron="0/5 * *  * * ? ")
    public void refresh(){

    }

}
