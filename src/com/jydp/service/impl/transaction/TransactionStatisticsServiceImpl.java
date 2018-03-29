package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionStatisticsDao;
import com.jydp.entity.DO.transaction.TransactionStatisticsDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.service.ITransactionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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

    /**
     * 查询交易统计记录条数(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @return 操作成功：返回交易统计记录条数，操作失败：返回0
     */
    public int countTransactionStatisticsForBack(int currencyId, Timestamp startTime, Timestamp endTime) {
        return transactionStatisticsDao.countTransactionStatisticsForBack(currencyId, startTime, endTime);
    }

    /**
     * 查询交易统计记录列表(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return 操作成功：返回交易统计记录，操作失败：返回null
     */
    public List<TransactionStatisticsDO> listTransactionStatisticsForBack(int currencyId, Timestamp startTime,
                                                                          Timestamp endTime, int pageNumber, int pageSize) {
        return transactionStatisticsDao.listTransactionStatisticsForBack(currencyId, startTime, endTime, pageNumber, pageSize);
    }

    /**
     * 批量新增交易统计
     * @param transactionStatisticsDOS  交易统计集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionStatisticsList(List<TransactionStatisticsDO> transactionStatisticsDOS){
        return transactionStatisticsDao.insertTransactionStatisticsList(transactionStatisticsDOS);
    }

    /**
     * 获取最后一条添加的时间
     * @return  操作成功：返回添加的时间，操作失败：返回null
     */
    public Timestamp getLastAddTime(){
        return transactionStatisticsDao.getLastAddTime();
    }

    /**
     * 获取最后一条添加的时间
     *
     * @param currencyId 币种Id
     * @return 操作成功：返回添加的时间，操作失败：返回null
     */
    public Timestamp getLastAddTimeByCurrencyId(int currencyId) {
        return transactionStatisticsDao.getLastAddTimeByCurrencyId(currencyId);
    }
}
