package com.jydp.service.impl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.service.ITradeCommonService;
import com.jydp.service.ITradeService;
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

    /**
     * 匹配交易（迭代）
     * @param order 挂单信息
     * @return JsonObjectBO 交易成功与否信息
     */
    public JsonObjectBO trade(TransactionPendOrderDO order){
        JsonObjectBO resultJson = new JsonObjectBO();

        double pendingNumber = order.getPendingNumber();
        double dealNumber = order.getDealNumber();
        double restNumber = pendingNumber - dealNumber;

        if(restNumber < 0){
            resultJson.setCode(5);
            resultJson.setMessage("数据异常");
            return resultJson;
        }else if(restNumber == 0){
            resultJson.setCode(1);
            resultJson.setMessage("该挂单已经交易完成");
            return resultJson;
        }

        //匹配交易
        resultJson = tradeService.tradeHandle(order);
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
