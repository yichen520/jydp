package com.jydp.quartz;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易页面数据 定时从数据库拉取到redis
 * @author hz
 *
 */
@Component
public class TransactionPageTimer {

	/** redis服务 */
	@Autowired
	private IRedisService redisService;

	/** 挂单记录 */
	@Autowired
	private ITransactionPendOrderService transactionPendOrderService;

	/** 交易币种 */
	@Autowired
	private ITransactionCurrencyService transactionCurrencyService;

	/** 挂单记录,买一价，卖一价（每5s执行一次） */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void transactionPendOrder(){
		//获取所有币种
		List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
		if(transactionCurrencyList.isEmpty()){
			return;
		}

		for (TransactionCurrencyDO transactionCurrency: transactionCurrencyList) {
			//买入记录
			List<TransactionPendOrderDO> transactionPendOrderBuyList =
					transactionPendOrderService.listLatestRecords(1,transactionCurrency.getCurrencyId(),15);

			//设置买入挂单记录key
			String buyKey = "transactionPendOrderBuyList" + transactionCurrency.getCurrencyId();
			//设置买一价key
			String buyOneKey = "buyOne" + transactionCurrency.getCurrencyId();

			List<Object> setBuyList = new ArrayList<>();
			if(transactionPendOrderBuyList.size() > 0){
				for (TransactionPendOrderDO transactionPendOrder: transactionPendOrderBuyList
						) {
					setBuyList.add(transactionPendOrder);
				}
			}

			if(setBuyList.size() == transactionPendOrderBuyList.size()){
				//删除挂单记录key
				redisService.deleteValue(buyKey);
				//插入最新挂单记录
				redisService.addList(buyKey,setBuyList);
				//删除买一价key
				redisService.deleteValue(buyOneKey);
				//插入最新买一价
				redisService.addValue(buyOneKey,transactionPendOrderBuyList.get(0).getPendingPrice());
			}

			//卖出记录
			List<TransactionPendOrderDO> transactionPendOrderSellList =
					transactionPendOrderService.listLatestRecords(2,transactionCurrency.getCurrencyId(),15);

			//设置卖出挂单记录key
			String sellKey = "transactionPendOrderSellList" + transactionCurrency.getCurrencyId();
			//设置卖一价key
			String sellOneKey = "sellOne" + transactionCurrency.getCurrencyId();

			List<Object> setSellList = new ArrayList<>();
			if(transactionPendOrderSellList.size() > 0){
				for (TransactionPendOrderDO transactionPendOrder: transactionPendOrderSellList
						) {
					setSellList.add(transactionPendOrder);
				}
			}

			if(setBuyList.size() == transactionPendOrderBuyList.size()){
				//清空该key
				redisService.deleteValue(sellKey);
				//插入最新数据
				redisService.addList(sellKey,setSellList);
				//删除卖一价key
				redisService.deleteValue(sellOneKey);
				//插入最新卖一价
				redisService.addValue(sellOneKey,transactionPendOrderSellList.get(0).getPendingPrice());
			}
		}
	}
	
}
