package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionDealRedisDao;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis成交记录
 * @author hz
 *
 */
@Repository
public class TransactionDealRedisDaoImpl implements ITransactionDealRedisDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId) {
        List<TransactionDealRedisDO> transactionDealRedisList = null;

        Map<String,Object> map = new HashMap<>();
        map.put("num", num);
        map.put("currencyId", currencyId);

        try {
            transactionDealRedisList = sqlSessionTemplate.selectList("TransactionDealRedis_listTransactionDealRedis",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionDealRedisList;
    }

    /**
     * 新增成交记录
     * @param transactionDealRedis  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedis(TransactionDealRedisDO transactionDealRedis){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionDealRedis_insertTransactionDealRedis", transactionDealRedis);
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 批量新增成交记录
     * @param redisDealList  成交记录集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedisList(List<TransactionDealRedisDO> redisDealList){
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("TransactionDealRedis_insertTransactionDealRedisList", redisDealList);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result == redisDealList.size()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 查询今日总成交数量
     * @param date 当前时间戳
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    public List<TransactionDealPriceDTO> getNowTurnover(Timestamp date){
        List<TransactionDealPriceDTO> result = null;

        try {
            result = sqlSessionTemplate.selectList("TransactionDealRedis_getNowTurnover", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询今日总交易额
     * @param date 当前时间戳
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    public List<TransactionDealPriceDTO> getNowVolumeOfTransaction(Timestamp date){
        List<TransactionDealPriceDTO> result = null;

        try {
            result = sqlSessionTemplate.selectList("TransactionDealRedis_getNowVolumeOfTransaction", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }


    /**
     * 查询今日最高价
     * @param date 今日时间戳
     * @return 查询成功：返回今日最高价，查询失败或今日最高价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayHighestPrice(Timestamp date){
        List<TransactionDealPriceDTO> result = null;

        try {
            result = sqlSessionTemplate.selectList("TransactionDealRedis_getTodayHighestPrice", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询今日最低价
     * @param date 今日时间戳
     * @return 查询成功：返回今日最低价，查询失败或今日最低价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayLowestPrice(Timestamp date){
        List<TransactionDealPriceDTO> result = null;

        try {
            result = sqlSessionTemplate.selectList("TransactionDealRedis_getTodayLowestPrice", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询当前时间上一个成交价格
     * @param date 需要查询的时间节点
     * @param endDate 查询时间节点开盘时间
     * @return 查询成功：返回上一个价格，查询失败或上一个价格为0：返回0
     */
    public List<TransactionDealPriceDTO> getNowLastPrice(Timestamp date, Timestamp endDate){
        List<TransactionDealPriceDTO> result = null;
        Map<String,Object> map = new HashMap<>();
        map.put("date", date);
        map.put("endDate", endDate);

        try {
            result = sqlSessionTemplate.selectList("TransactionDealRedis_getNowLastPrice", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据订单号查询记录
     * @param orderNo  订单号
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealRedisByOrderNo(String orderNo){
        List<TransactionDealRedisDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("TransactionDealRedis_listTransactionDealRedisByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 根据订单号删除记录
     * @param orderNo 订单号
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean deleteDealByOrderNo(String orderNo){
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("TransactionDealRedis_deleteDealByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return  false;
        }
    }

    /**
     * 获取盛源交易所 日成交总价，日成交总数量
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public TransactionBottomPriceDTO getBottomPrice(int currencyId, String orderNoPrefix,
                                                    Timestamp startTime, Timestamp endTime) {
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("orderNoPrefix", orderNoPrefix);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        TransactionBottomPriceDTO result = null;
        try {
            result = sqlSessionTemplate.selectOne("TransactionDealRedis_getBottomPrice", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 获取盛源交易所 当前价
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @return 查询成功：返回当前价格，查询失败或当前价格为0：返回0
     */
    public double getCurrentPrice(int currencyId, String orderNoPrefix){
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("orderNoPrefix", orderNoPrefix);

        double result = 0;
        try {
            result = sqlSessionTemplate.selectOne("TransactionDealRedis_getCurrentPrice", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * k线图数据拉取
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public List<TransactionDealRedisDTO> listTransactionUserDealForKline(int currencyId){
        List<TransactionDealRedisDTO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listTransactionUserDealForKline", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询未来num条redis成交记录
     * @param paymentType  交易类型
     * @param currencyId  币种Id
     * @param date  查询时间
     * @param num  查询条数
     * @return  操作成功：返回数据集合，操作失败:返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealForPending(int paymentType, int currencyId, Timestamp date, int num){
        List<TransactionDealRedisDO> resultList = null;

        Map<String,Object> map = new HashMap<>();
        map.put("paymentType", paymentType);
        map.put("currencyId", currencyId);
        map.put("date", date);
        map.put("num", num);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listTransactionDealForPending", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  resultList;
    }

    /**
     * 每日交易统计
     * @param orderNoPrefix  订单号开头
     * @param date  昨日凌晨
     * @param endDate  今日凌晨
     * @return  操作成功：返回统计集合，操作失败：返回null
     */
    public List<TransactionBottomPriceDTO> listStatistics(String orderNoPrefix, Timestamp date, Timestamp endDate){
        List<TransactionBottomPriceDTO> resultList = null;

        Map<String,Object> map = new HashMap<>();
        map.put("endDate", endDate);
        map.put("orderNoPrefix", orderNoPrefix);
        map.put("date", date);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listStatistics", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  resultList;
    }

    /**
     * 验证币种是否已存在交易
     * @param currencyId 币种id
     * @return 已存在交易：返回true，不存在交易：返回false
     */
    public boolean validateGuidancePrice(int currencyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("addTime", DateUtil.getCurrentTime());

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("TransactionDealRedis_validateGuidancePrice", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        }
        return false;
    }

}
