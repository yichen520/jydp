package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ITransactionDealRedisDao;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.service.ITransactionDealRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * redis成交记录
 * @author fk
 *
 */
@Service("transactionDealRedisService")
public class TransactionDealRedisServiceImpl implements ITransactionDealRedisService {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisDao transactionDealRedisDao;

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId){
        return transactionDealRedisDao.listTransactionDealRedis(num, currencyId);
    }

    /**
     * 新增成交记录
     * @param orderNo  记录号
     * @param paymentType  收支类型
     * @param currencyId  币种Id
     * @param transactionPrice  成交单价
     * @param currencyNumber  成交数量
     * @param currencyTotalPrice  成交总价
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedis(String orderNo, int paymentType, int currencyId,
                                       double transactionPrice, double currencyNumber, double currencyTotalPrice,
                                       Timestamp addTime){
        TransactionDealRedisDO dealRedisDO = new TransactionDealRedisDO();
        dealRedisDO.setOrderNo(orderNo);
        dealRedisDO.setPaymentType(paymentType);
        dealRedisDO.setCurrencyId(currencyId);
        dealRedisDO.setTransactionPrice(transactionPrice);
        dealRedisDO.setCurrencyNumber(currencyNumber);
        dealRedisDO.setCurrencyTotalPrice(currencyTotalPrice);
        dealRedisDO.setAddTime(addTime);
        return transactionDealRedisDao.insertTransactionDealRedis(dealRedisDO);
    }

    /**
     * 批量新增成交记录
     * @param redisDealList  成交记录集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedisList(List<TransactionDealRedisDO> redisDealList){
        return transactionDealRedisDao.insertTransactionDealRedisList(redisDealList);
    }

    /**
     * 查询24小时总成交数量
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    public double getNowTurnover(){
        Timestamp date = DateUtil.getCurrentTime();
        long newDate = 1000L * 60 * 60 * 24;
        newDate = date.getTime() - newDate;
        date = DateUtil.longToTimestamp(newDate);
        return transactionDealRedisDao.getNowTurnover(date);
    }

    /**
     * 查询24小时总交易额
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    public double getNowVolumeOfTransaction(){
        Timestamp date = DateUtil.getCurrentTime();
        long newDate = 1000L * 60 * 60 * 24;
        newDate = date.getTime() - newDate;
        date = DateUtil.longToTimestamp(newDate);
        return transactionDealRedisDao.getNowVolumeOfTransaction(date);
    }
}
