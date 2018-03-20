package com.jydp.quartz;


import com.iqmkj.utils.DateUtil;
import com.jydp.service.ITransactionRedisDealCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 每日交易统计
 * @author fk
 */
@Component
public class TransactionStatisticsTimer {

    /** 成交记录公共服务 */
    @Autowired
    private ITransactionRedisDealCommonService transactionRedisDealCommonService;

    /** 每日2点交易统计 */
    @Scheduled(cron="0 0 2 * * ? ")
    public void statisTicsTim(){
        boolean execuBoo = false;

        //循环失败则继续统计5次
        for(int i = 0; i < 5; i++){
            if (execuBoo){
                break;
            }
            execuBoo = transactionRedisDealCommonService.statistics();
        }
    }

}
