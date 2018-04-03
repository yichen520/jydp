package com.jydp.service.impl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.service.ITradeCommonService;
import com.jydp.service.ITradeService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 匹配交易（迭代）
 * @author hz
 *
 */
@Service("tradeCommonService")
public class TradeCommonServiceImpl implements ITradeCommonService {

    /** 匹配交易（单笔） */
    @Autowired
    private ITradeService tradeService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /**
     * 匹配交易（迭代）
     * @param order 挂单信息
     * @return JsonObjectBO 交易成功与否信息
     */
    public JsonObjectBO trade(TransactionPendOrderDO order){
        JsonObjectBO resultJson = new JsonObjectBO();

        double pendingNumber = order.getPendingNumber();
        double dealNumber = order.getDealNumber();
        double restNumber = BigDecimalUtil.sub(pendingNumber, dealNumber);

        if(restNumber < 0){
            resultJson.setCode(5);
            resultJson.setMessage("数据异常");
            return resultJson;
        }else if(restNumber == 0){
            resultJson.setCode(1);
            resultJson.setMessage("该挂单已经交易完成");
            return resultJson;
        }

        //获取该挂单里信息
        int currencyId = order.getCurrencyId();
        int paymentType = order.getPaymentType();
        int pendingStatus = order.getPendingStatus();

        if(pendingStatus != 1 && pendingStatus != 2){
            resultJson.setCode(4);
            resultJson.setMessage("该挂单不在交易状态");
            return resultJson;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("没有该币种");
            return resultJson;
        }
        //获取卖出手续费
        double sellFee = transactionCurrency.getSellFee();

        //获取对应的最新的挂单记录
        int matchPaymentType = 0;
        if(paymentType == 1){
            matchPaymentType = 2;
        }else if (paymentType == 2){
            matchPaymentType = 1;
        }
        TransactionPendOrderDO matchOrder = transactionPendOrderService.getLastTransactionPendOrder(0, currencyId, matchPaymentType);
        if(matchOrder == null){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
            return resultJson;
        }
        //如果匹配不上，直接返回false
        if((paymentType == 1 && order.getPendingPrice() < matchOrder.getPendingPrice()) ||
                (paymentType == 2 && order.getPendingPrice() > matchOrder.getPendingPrice())){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
            return resultJson;
        }

        //匹配交易
        resultJson = tradeService.tradeHandle(order, matchOrder, sellFee);
        int code = resultJson.getCode();
        JSONObject data = resultJson.getData();
        TransactionPendOrderDO returnOrder = null;
        if(data != null) {
            returnOrder = JSON.parseObject(data.toString(), TransactionPendOrderDO.class);
        }

        //如果交易成功，继续匹配
        if(code == 1){
            if(returnOrder != null){
                double dealNum = returnOrder.getDealNumber();
                if(dealNum > 0){
                    resultJson = trade(returnOrder);
                }
            }
        }

        return resultJson;
    }

}
