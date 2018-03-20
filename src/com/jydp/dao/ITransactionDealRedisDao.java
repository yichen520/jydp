package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * redis成交记录
 * @author hz
 *
 */
public interface ITransactionDealRedisDao {

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId);

    /**
     * 新增成交记录
     * @param transactionDealRedis  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionDealRedis(TransactionDealRedisDO transactionDealRedis);

    /**
     * 批量新增成交记录
     * @param redisDealList  成交记录集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionDealRedisList(List<TransactionDealRedisDO> redisDealList);


    /**
     * 查询今日总成交数量
     * @param date 当前时间戳
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    List<TransactionDealPriceDTO> getNowTurnover(Timestamp date);

    /**
     * 查询今日总交易额
     * @param date 当前时间戳
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    List<TransactionDealPriceDTO> getNowVolumeOfTransaction(Timestamp date);

    /**
     * 查询今日最高价
     * @param date 今日时间戳
     * @return 查询成功：返回今日最高价，查询失败或今日最高价为0：返回0
     */
    List<TransactionDealPriceDTO> getTodayHighestPrice(Timestamp date);

    /**
     * 查询今日最低价
     * @param date 今日时间戳
     * @return 查询成功：返回今日最低价，查询失败或今日最低价为0：返回0
     */
    List<TransactionDealPriceDTO> getTodayLowestPrice(Timestamp date);

    /**
     * 查询当前时间上一个成交价格
     * @param date 需要查询的时间节点
     * @param endDate 查询时间节点开盘时间
     * @return 查询成功：返回上一个价格，查询失败或上一个价格为0：返回0
     */
    List<TransactionDealPriceDTO> getNowLastPrice(Timestamp date, Timestamp endDate);

    /**
     * 根据订单号查询记录
     * @param orderNo  订单号
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    List<TransactionDealRedisDO> listTransactionDealRedisByOrderNo(String orderNo);

    /**
     * 根据订单号删除记录
     * @param orderNo 订单号
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean deleteDealByOrderNo(String orderNo);

    /**
     * 获取盛源交易所 当日成交总价，当日成交总数量
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    TransactionBottomPriceDTO getBottomPriceToday(int currencyId, String orderNoPrefix);

    /**
     * 获取盛源交易所 当前价
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @return 查询成功：返回当前价格，查询失败或当前价格为0：返回0
     */
    double getCurrentPrice(int currencyId, String orderNoPrefix);

    /**
     * k线图数据拉取
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    List<TransactionDealRedisDTO> listTransactionUserDealForKline(int currencyId);

    /**
     * 查询未来num条redis成交记录
     * @param paymentType  交易类型
     * @param currencyId  币种Id
     * @param date  查询时间
     * @param num  查询条数
     * @return  操作成功：返回数据集合，操作失败:返回null
     */
    List<TransactionDealRedisDO> listTransactionDealForPending(int paymentType, int currencyId, Timestamp date, int num);

    /**
     * 每日交易统计
     * @param orderNoPrefix  订单号开头
     * @param date  昨日凌晨
     * @param endDate  今日凌晨
     * @return  操作成功：返回统计集合，操作失败：返回null
     */
    List<TransactionBottomPriceDTO> listStatistics(String orderNoPrefix, Timestamp date, Timestamp endDate);
}
