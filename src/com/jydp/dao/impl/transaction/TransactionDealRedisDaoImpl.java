package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionDealRedisDao;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
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
     * 查询24小时总成交数量
     * @param date 当前时间戳
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    public double getNowTurnover(Timestamp date){
        double result = 0;

        try {
            result = sqlSessionTemplate.selectOne("TransactionDealRedis_getNowTurnover", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询24小时总交易额
     * @param date 当前时间戳
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    public double getNowVolumeOfTransaction(Timestamp date){
        double result = 0;

        try {
            result = sqlSessionTemplate.selectOne("TransactionDealRedis_getNowVolumeOfTransaction", date);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
