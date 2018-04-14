package com.iqmkj.utils;

import com.jydp.other.RabbitProducer;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
/**
 * rabbit队列服务
 * 
 * @version 1.0
 * @author 
 */
public class RabbitUtils {
	
	private static RabbitProducer rabbitProducer = SpringUtil.getBean(RabbitProducer.class);
	
	private RabbitUtils() {
	}
	/**
	 * 订单数据处理
	 * @param transactionPendOrder
	 */
	public static void trdeOrder(TransactionPendOrderDO transactionPendOrder) {
		rabbitProducer.send("trade_order_key",transactionPendOrder);
	}


	
}
