package com.jydp.quartz;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * 币种上线
 * @author fk
 */
@Component
public class CurrencyExcTimer {

    /** 币种信息 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 币种上线 */
    //@Scheduled(cron="0/59 * *  * * ? ")
    public void transferMarketRate(){
        List<TransactionCurrencyDO> transactionCurrencyDOS = transactionCurrencyService.listTransactionCurrencyAll();
        if (transactionCurrencyDOS != null && transactionCurrencyDOS.size() > 0) {
            for (TransactionCurrencyDO curr: transactionCurrencyDOS) {
                if (curr.getUpTime().getTime() <= DateUtil.getCurrentTime().getTime() && curr.getUpStatus() != 2 && curr.getPaymentType() != 1){

                    transactionCurrencyService.updateUpStatus(curr.getCurrencyId(), 2, null, null, null);
                }
            }
        }
    }

}
