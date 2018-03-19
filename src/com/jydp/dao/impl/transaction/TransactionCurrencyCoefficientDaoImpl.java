package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionCurrencyCoefficientDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 币种系数
 * @author fk
 *
 */
@Repository
public class TransactionCurrencyCoefficientDaoImpl implements ITransactionCurrencyCoefficientDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增币种系数
     * @param transactionCurrencyCoefficient  币种系数
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrencyCoefficient(TransactionCurrencyCoefficientDO transactionCurrencyCoefficient){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("CurrencyCoefficient_insertTransactionCurrencyCoefficient", transactionCurrencyCoefficient);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据记录号查询币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    public TransactionCurrencyCoefficientDO getTransactionCurrencyCoefficientByOrderNo(String orderNo){
        TransactionCurrencyCoefficientDO transactionCurrencyCoefficient = null;

        try {
            transactionCurrencyCoefficient = sqlSessionTemplate.selectOne("CurrencyCoefficient_getTransactionCurrencyCoefficientByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  transactionCurrencyCoefficient;
    }

    /**
     * 查询币种系数条数
     * @return  操作成功：返回币种系数条数，操作失败：返回0
     */
    public int countTransactionCurrencyCoeffieientForBack(){
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("CurrencyCoefficient_countTransactionCurrencyCoeffieientForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  result;
    }

    /**
     * 查询币种系数集合
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回币种系数集合，操作失败：返回null
     */
    public List<TransactionCurrencyCoefficientDO> listTransactionCurrencyCoefficientForBack(int pageNumber, int pageSize){
        List<TransactionCurrencyCoefficientDO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("CurrencyCoefficient_listTransactionCurrencyCoefficientForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  resultList;
    }

    /**
     * 根据币种Id查询最新的币种系数
     * @param currencyId  币种Id
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    public TransactionCurrencyCoefficientDO getCurrencyCoefficientByCurrencyId(int currencyId){
        TransactionCurrencyCoefficientDO transactionCurrencyCoefficient = null;

        try {
            transactionCurrencyCoefficient = sqlSessionTemplate.selectOne("CurrencyCoefficient_getCurrencyCoefficientByCurrencyId", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  transactionCurrencyCoefficient;
    }

    /**
     * 根据记录号删除币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyCoefficientByOrderNo(String orderNo){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("CurrencyCoefficient_deleteTransactionCurrencyCoefficientByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

}
