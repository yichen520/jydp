package com.jydp.service;

import com.alibaba.fastjson.JSONArray;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.DTO.TransactionCurrencyDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import com.jydp.entity.VO.TransactionGraphVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * redis成交记录
 * @author fk
 *
 */
public interface ITransactionDealRedisService {

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId);

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
    boolean insertTransactionDealRedis(String orderNo, int paymentType, int currencyId,
                                       double transactionPrice, double currencyNumber, double currencyTotalPrice,
                                        Timestamp addTime);

    /**
     * 批量新增成交记录
     * @param redisDealList  成交记录集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionDealRedisList(List<TransactionDealRedisDO> redisDealList);

    /**
     * 查询今日总成交数量
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    List<TransactionDealPriceDTO> getNowTurnover();

    /**
     * 查询今日总交易额
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    List<TransactionDealPriceDTO> getNowVolumeOfTransaction();

    /**
     * 查询今日最高价
     * @return 查询成功：返回今日最高价，查询失败或今日最高价为0：返回0
     */
    List<TransactionDealPriceDTO> getTodayHighestPrice();

    /**
     * 查询今日最低价
     * @return 查询成功：返回今日最低价，查询失败或今日最低价为0：返回0
     */
    List<TransactionDealPriceDTO> getTodayLowestPrice();

    /**
     * 查询当前时间上一个成交价格
     * @param getDate 需要查询的时间节点
     * @return 查询成功：返回上一个价格，查询失败或上一个价格为0：返回0
     */
    List<TransactionDealPriceDTO> getNowLastPrice(Timestamp getDate);

    /**
     * 验证币种是否有历史交易
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean validateGuidancePrice(int currencyId);

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
     * 获取盛源交易所 日成交总价，日成交总数量
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    TransactionBottomPriceDTO getBottomPrice(int currencyId, String orderNoPrefix,
                                             Timestamp startTime, Timestamp endTime);

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
     * 从redis获取k线图数据
     * @param currencyId  币种Id
     * @param node  时间节点 ：5分钟 5m、15分钟 15m、30分钟 30m、1小时 1h、4小时 4h、1天 1d 、1周 1w
     * @return 操作成功:返回k线图数据, 操作失败:返回null
     */
    JSONArray gainGraphDataWithNode(int currencyId, String node);

    /**
     * 从redis获取k线图数据
     * @param currencyId  币种Id
     * @param node  时间节点 ：5分钟 5m、15分钟 15m、30分钟 30m、1小时 1h、4小时 4h、1天 1d 、1周 1w
     * @return 操作成功:返回k线图数据, 操作失败:返回null
     */
    List<TransactionGraphVO> gainGraphData(int currencyId, String node);

    /**
     * 每日交易统计
     * @param orderNoPrefix  订单号开头
     * @param date  昨日凌晨
     * @param endDate  今日凌晨
     * @return  操作成功：返回统计集合，操作失败：返回null
     */
    List<TransactionBottomPriceDTO> listStatistics(String orderNoPrefix, Timestamp date, Timestamp endDate);

    /**
     * 查询该币种最早的交易时间
     * @param currencyId 币种id
     * @param prefix 记录号前缀（区别后台做单，还是用户挂单）可为null
     * @return 操作成功：返回最早的交易时间，操作失败或无交易记录：返回null
     */
    Timestamp getEarliestTime(int currencyId, String prefix);

    /**
     * 查询成交记录
     * @param currencyId 币种Id
     * @param starTime 开始时间
     * @param endTime 结束时间
     * @return 操作成功：返回统计集合，操作失败：返回null
     */
    List<TransactionDealRedisDTO> listTransactionDealRedisForTimer(int currencyId, Timestamp starTime,
                                                                   Timestamp endTime);

    /**
     * 查询基准货币信息
     * @return 查询成功：返回基准货币信息，查询失败：返回null
     */
    List<TransactionCurrencyDealPriceDTO> getTransactionCurrencyDealPrice();
}
