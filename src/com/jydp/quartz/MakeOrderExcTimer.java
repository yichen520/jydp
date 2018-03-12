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
        List<TransactionMakeOrderVO> transactionMakeOrderList = transactionMakeOrderService.listTransactionMakeOrderForBack(null, 1, 0, null, null, null, null,null, 0,20);
        List<String> list = new ArrayList<>();
        if (transactionMakeOrderList != null && transactionMakeOrderList.size() > 0) {
            for (TransactionMakeOrderVO order: transactionMakeOrderList) {
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
