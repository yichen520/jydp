package com.jydp.quartz;

import com.iqmkj.utils.BigDecimalUtil;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ITradeCommonService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 交易补救措施
 * @author hz
 *
 */
@Component
public class TradeRemedyTimer {

	/** 挂单记录 */
	@Autowired
	private ITransactionPendOrderService transactionPendOrderService;

	/** 匹配交易 */
	@Autowired
	private ITradeCommonService tradeCommonService;

	/** 交易币种 */
	@Autowired
	private ITransactionCurrencyService transactionCurrencyService;

	/** 交易补救（每5s执行一次） */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void transactionPendOrder(){
		//获取所有币种
		List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
		if(transactionCurrencyList == null){
			return;
		}

		for (TransactionCurrencyVO transactionCurrency: transactionCurrencyList) {
			TransactionPendOrderDO order = null;
			//最高买入委托
			TransactionPendOrderDO buyOrder = transactionPendOrderService.getLastTransactionPendOrder(
					0, transactionCurrency.getCurrencyId(), 1);
			//最低卖出委托
			TransactionPendOrderDO sellOrder = transactionPendOrderService.getLastTransactionPendOrder(
					0, transactionCurrency.getCurrencyId(), 2);

			if(buyOrder == null || sellOrder == null){
				continue;
			}
			//如果无法匹配，则不交易
			if(buyOrder.getPendingPrice() < sellOrder.getPendingPrice()){
				continue;
			}

			double buyNum = BigDecimalUtil.sub(buyOrder.getPendingNumber(), buyOrder.getDealNumber());
			double sellNum = BigDecimalUtil.sub(sellOrder.getPendingNumber(), sellOrder.getDealNumber());
			long buyTime = buyOrder.getAddTime().getTime();
			long sellTime = sellOrder.getAddTime().getTime();
			//时间优先
			if(sellTime > buyTime){
				order = buyOrder;
			}else if(sellTime < buyTime){
				order = sellOrder;
			}if(sellTime == buyTime){
				//数量优先
				if(buyNum >= sellNum){
					order = buyOrder;
				}else if(buyNum < sellNum){
					order = sellOrder;
				}
			}
			//交易
			tradeCommonService.trade(order);
		}

	}
	
}
