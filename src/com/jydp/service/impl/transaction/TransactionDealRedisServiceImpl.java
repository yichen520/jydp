package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ITransactionDealRedisDao;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.service.ITransactionDealRedisService;
import config.RedisKeyConfig;
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
    public List<TransactionDealPriceDTO> getNowTurnover(){
        Timestamp date = DateUtil.getCurrentTime();
        long newDate = date.getTime() - RedisKeyConfig.DAY_TIME;
        date = DateUtil.longToTimestamp(newDate);
        return transactionDealRedisDao.getNowTurnover(date);
    }

    /**
     * 查询24小时总交易额
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    public List<TransactionDealPriceDTO> getNowVolumeOfTransaction(){
        Timestamp date = DateUtil.getCurrentTime();
        long newDate = date.getTime() - RedisKeyConfig.DAY_TIME;
        date = DateUtil.longToTimestamp(newDate);
        return transactionDealRedisDao.getNowVolumeOfTransaction(date);
    }

    /**
     * 查询今日最高价
     * @return 查询成功：返回今日最高价，查询失败或今日最高价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayHighestPrice(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getTodayHighestPrice(date);
    }

    /**
     * 查询今日最低价
     * @return 查询成功：返回今日最低价，查询失败或今日最低价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayLowestPrice(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getTodayLowestPrice(date);
    }

    /**
     * 查询当前时间上一个成交价格
     * @param getDate 需要查询的时间节点
     * @return 查询成功：返回上一个价格，查询失败或上一个价格为0：返回0
     */
    public List<TransactionDealPriceDTO> getNowLastPrice(Timestamp getDate){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = getDate.getTime() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getNowLastPrice(getDate, date);
    }

}
