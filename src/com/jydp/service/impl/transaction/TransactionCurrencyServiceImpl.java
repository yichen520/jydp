package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 交易币种
 * @author fk
 *
 */
@Service("transactionCurrencyService")
public class TransactionCurrencyServiceImpl implements ITransactionCurrencyService{

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyDao transactionCurrencyDao;

    /**
     * 新增交易币种
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrency(String currencyShortName, String currencyName, Timestamp addTime){
        TransactionCurrencyDO transactionCurrency = new TransactionCurrencyDO();
        transactionCurrency.setCurrencyName(currencyName);
        transactionCurrency.setCurrencyShortName(currencyShortName);
        transactionCurrency.setAddTime(addTime);

        return transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
    }

    /**
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyDO getTransactionCurrencyByCurrencyId(int currencyId){
        return transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
    }

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        return transactionCurrencyDao.updateTransactionCurrency(transactionCurrency);
    }

    /**
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyByCurrencyId(int currencyId){
        return transactionCurrencyDao.deleteTransactionCurrencyByCurrencyId(currencyId);
    }

    /**
     * 获取所有币种信息(web端用户注册时使用)
     * @return 查询成功：返回币种信息列表；查询失败：返回null
     */
    @Override
    public List<TransactionCurrencyDO> getTransactionCurrencyListForWeb() {
        return null;
    }
}
