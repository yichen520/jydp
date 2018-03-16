package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionStatisticsDao;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.service.ITransactionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:交易统计记录表
 * Author: hht
 * Date: 2018-03-16 9:36
 */
@Service("transactionStatisticsService")
public class TransactionStatisticsServiceImpl implements ITransactionStatisticsService {

    /** 交易统计记录表 */
    @Autowired
    private ITransactionStatisticsDao transactionStatisticsDao;

    /**
     * 获取盛源交易所 历史当日成交总价*历史当日系数，历史当日成交总数量
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public TransactionBottomPriceDTO getBottomPricePast(int currencyId){
        return transactionStatisticsDao.getBottomPricePast(currencyId);
    }
}
