package com.jydp.quartz;

import com.jydp.service.ITransactionPendOrderCommonService;
import com.jydp.service.ITransactionRedisDealCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 交易页面数据 定时从数据库拉取到redis
 * @author hz
 *
 */
@Component
public class TransactionPageTimer {

	/** 从数据库拉取 挂单记录 到redis */
	@Autowired
	private ITransactionPendOrderCommonService transactionPendOrderCommonService;

	/** 数据库拉取 成交记录 到redis */
	@Autowired
	private ITransactionRedisDealCommonService transactionRedisDealCommonService;

	/** 成交记录  每5秒刷新一次 */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void refresh() {transactionRedisDealCommonService.userDealForRedis();}

	/** 挂单记录,买一价，卖一价（每5s执行一次） */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void transactionPendOrder(){
		transactionPendOrderCommonService.getPendOrder();
	}
	
}
