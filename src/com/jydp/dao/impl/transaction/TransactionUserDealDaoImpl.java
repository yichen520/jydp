package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成交记录
 * @author fk
 *
 */
@Repository
public class TransactionUserDealDaoImpl implements ITransactionUserDealDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询用户成交记录
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDO> getTransactionUserDeallist(int userId, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<TransactionUserDealDO> transactionUserDealList = null;
        try {
            transactionUserDealList = sqlSessionTemplate.selectList("TransactionUserDeal_getTransactionUserDeallist",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealList;
    }

    /**
     * 新增成交记录
     * @param transactionUserDeal  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionUserDeal(TransactionUserDealDO transactionUserDeal){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionUserDeal_insertTransactionUserDeal", transactionUserDeal);
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
     * 查询成交记录条数(后台)
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    public int countTransactionUserDealForBack(String orderNo, String userAccount, int paymentType, String currencyName,
                                        Timestamp startAddTime, Timestamp endAddTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("userAccount", userAccount);
        map.put("paymentType", paymentType);
        map.put("currencyName", currencyName);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);

        try {
            result = sqlSessionTemplate.selectOne("TransactionUserDeal_countTransactionUserDealForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询成交记录(后台)
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    public List<TransactionUserDealDO> listTransactionUserDealForBack(String orderNo, String userAccount, int paymentType, String currencyName,
                                                               Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize){
        List<TransactionUserDealDO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("userAccount", userAccount);
        map.put("paymentType", paymentType);
        map.put("currencyName", currencyName);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listTransactionUserDealForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }


}
