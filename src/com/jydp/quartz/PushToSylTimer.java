package com.jydp.quartz;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.service.IJydpToSylService;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 向盛源链钱包平台推送
 * @author hz
 */
@Component
public class PushToSylTimer {

    /** JYDP转账盛源链(JYDP-->SYL) */
    @Autowired
    private IJydpToSylService jydpToSylService;

    /** 交易大盘向盛源链钱包转币申请推送 */
    @Scheduled(cron="0/10 * *  * * ? ")
    public void transferMarketRate(){
        jydpToSylService.jydpToSylApply();
    }

}
