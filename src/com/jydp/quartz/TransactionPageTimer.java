package com.jydp.quartz;

import com.jydp.service.IKGraphCommonService;
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

	/** k线图统计数据公共服务 */
	@Autowired
	private IKGraphCommonService kGraphCommonService;

	/** 成交记录  每5秒刷新一次 */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void refresh() {transactionRedisDealCommonService.userDealForRedis();}

	/** 挂单记录,买一价，卖一价（每5s执行一次） */
	@Scheduled(cron="0/5 * *  * * ? ")
	public void transactionPendOrder(){
		transactionPendOrderCommonService.getPendOrder();
	}

	/** 组装基准信息参数并存入redis (当前交易价,今日涨跌,今日最高价,今日最低价,今日成交量)*/
	@Scheduled(cron="0/5 * *  * * ? ")
	public void standardMessageForRedis(){
		transactionRedisDealCommonService.standardMessageForRedis();
	}

    /** 每日开盘基准信息重置(昨日收盘价) */
    @Scheduled(cron="0 0 8  * * ? ")  //（功能待定）
    public void updateWeeHoursBasisOfPrice(){
        transactionRedisDealCommonService.updateWeeHoursBasisOfPrice();
    }

	/** k线图数据统计存入统计表和redis （五分钟一次） */
	@Scheduled(cron="0 0/5 *  * * ? ")
	public void graphDataForRedis(){
		kGraphCommonService.exeKLineGraphForTimer();
	}
	
}
