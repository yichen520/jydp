package com.jydp.quartz;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.VO.TransactionMakeOrderVO;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionMakeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 做单执行
 * @author fk
 */
@Component
public class MakeOrderExcTimer {

    /** 做单记录 */
    @Autowired
    private ITransactionMakeOrderService transactionMakeOrderService;

    /** 执行做单 */
    @Scheduled(cron="0/15 * *  * * ? ")
    public void executeMakeOrderExc(){
        List<TransactionMakeOrderDO> transactionMakeOrderList = transactionMakeOrderService.listTransactionMakeOrderForBack(null, null, 1, null, DateUtil.getCurrentTime(), 0, 5);
        List<String> list = new ArrayList<>();
        if (transactionMakeOrderList != null && transactionMakeOrderList.size() > 0) {
            for (TransactionMakeOrderDO order: transactionMakeOrderList) {
                if (order.getExecuteTime().getTime() <= DateUtil.getCurrentTime().getTime() && order.getExecuteStatus() == 1){
                    list.add(order.getOrderNo());
                }
            }
        }
        //执行做单
        if (list.size() > 0) {
            transactionMakeOrderService.executeMakeOrderMore(list);
        }
    }

}
