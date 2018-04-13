package com.jydp.mqListener;

import com.iqmkj.utils.LogUtil;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import org.springframework.stereotype.Component;
import  com.jydp.service.ITradeCommonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日常数据队列监听
 * 
 * 
 */
@Component
public class TradeOrderListener implements MqListener<TransactionPendOrderDO> {

	/** 匹配交易 */
	@Autowired
	private ITradeCommonService tradeCommonService;

	@Override
	public void listen(TransactionPendOrderDO model) {
		LogUtil.printInfoLog("rabbitMQ队列加入挂单号："+model.getPendingOrderNo());
		tradeCommonService.trade(model);
	}
}
