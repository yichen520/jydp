package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易币种
 * @author fk
 *
 */
@Repository
public class TransactionCurrencyDaoImpl implements ITransactionCurrencyDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增交易币种
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionCurrency_insertTransactionCurrency", transactionCurrency);
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
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyDO getTransactionCurrencyByCurrencyId(int currencyId){
        TransactionCurrencyDO transactionCurrency = null;

        try {
            transactionCurrency = sqlSessionTemplate.selectOne("TransactionCurrency_getTransactionCurrencyByCurrencyId", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return transactionCurrency;
    }

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_updateTransactionCurrency", transactionCurrency);
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
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyByCurrencyId(int currencyId){
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("TransactionCurrency_deleteTransactionCurrencyByCurrencyId", currencyId);
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
     * 获取所有币种信息(web端用户注册时使用)
     * @return 查询成功：返回币种信息列表；查询失败：返回null
     */
    @Override
    public List<TransactionCurrencyDO> getTransactionCurrencyListForWeb() {
        List<TransactionCurrencyDO> transactionCurrencyDOList = null;

        try {
            transactionCurrencyDOList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyListForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionCurrencyDOList;
    }
}
