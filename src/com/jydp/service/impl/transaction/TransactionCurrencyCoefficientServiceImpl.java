package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionCurrencyCoefficientDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyCoefficientDO;
import com.jydp.service.ITransactionCurrencyCoefficientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 币种系数
 * @author fk
 *
 */
@Service("transactionCurrencyCoefficientService")
public class TransactionCurrencyCoefficientServiceImpl implements ITransactionCurrencyCoefficientService {

    /** 币种系数 */
    @Autowired
    private ITransactionCurrencyCoefficientDao transactionCurrencyCoefficientDao;

    /**
     * 新增币种系数
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param currencyId  币种Id
     * @param currencyName  货币名称
     * @param currencyCoefficient  币种系数
     * @param backerAccount  后台管理员帐号
     * @param ipAddress  操作时的ip地址
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrencyCoefficient(String orderNo, int currencyId, String currencyName,
                                                 double currencyCoefficient, String backerAccount,
                                                 String ipAddress, Timestamp addTime){
        TransactionCurrencyCoefficientDO transactionCurrencyCoefficient = new TransactionCurrencyCoefficientDO();
        transactionCurrencyCoefficient.setOrderNo(orderNo);
        transactionCurrencyCoefficient.setCurrencyId(currencyId);
        transactionCurrencyCoefficient.setCurrencyName(currencyName);
        transactionCurrencyCoefficient.setCurrencyCoefficient(currencyCoefficient);
        transactionCurrencyCoefficient.setBackerAccount(backerAccount);
        transactionCurrencyCoefficient.setIpAddress(ipAddress);
        transactionCurrencyCoefficient.setAddTime(addTime);

        return transactionCurrencyCoefficientDao.insertTransactionCurrencyCoefficient(transactionCurrencyCoefficient);
    }

    /**
     * 根据记录号查询币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    public TransactionCurrencyCoefficientDO getTransactionCurrencyCoefficientByOrderNo(String orderNo){
        return transactionCurrencyCoefficientDao.getTransactionCurrencyCoefficientByOrderNo(orderNo);
    }

    /**
     * 查询币种系数条数
     * @param currencyId 币种Id,查询全部填0
     * @param startAddTime  起始添加时间
     * @param endAddTime  结束添加时间
     * @return  操作成功：返回币种系数条数，操作失败：返回0
     */
    public int countTransactionCurrencyCoeffieientForBack(int currencyId, Timestamp startAddTime, Timestamp endAddTime){
        return  transactionCurrencyCoefficientDao.countTransactionCurrencyCoeffieientForBack(currencyId, startAddTime, endAddTime);
    }

    /**
     * 查询币种系数集合
     * @param currencyId 币种Id,查询全部填0
     * @param startAddTime  起始添加时间
     * @param endAddTime  结束添加时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回币种系数集合，操作失败：返回null
     */
    public List<TransactionCurrencyCoefficientDO> listTransactionCurrencyCoefficientForBack(int currencyId, Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize){
        return transactionCurrencyCoefficientDao.listTransactionCurrencyCoefficientForBack(currencyId, startAddTime, endAddTime, pageNumber, pageSize);
    }

    /**
     * 根据币种Id查询之前最新的币种系数
     * @param currencyId  币种Id
     * @param date 要查询的时间
     * @return  操作成功：返回币种系数，操作失败：返回null
     */
    public TransactionCurrencyCoefficientDO getCurrencyCoefficientByCurrencyId(int currencyId, Timestamp date) {
        return transactionCurrencyCoefficientDao.getCurrencyCoefficientByCurrencyId(currencyId, date);
    }

    /**
     * 根据记录号删除币种系数
     * @param orderNo  记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyCoefficientByOrderNo(String orderNo){
        return transactionCurrencyCoefficientDao.deleteTransactionCurrencyCoefficientByOrderNo(orderNo);
    }

    /**
     * 查询每个币种最近的系数
     * @param date  起始时间  非null则这个时间点之前最近的一条
     * @return  操作成功：返回币种系数集合，操作失败：返回null
     */
    public List<TransactionCurrencyCoefficientDO> listTransactionCurrencyCoefficientForNew(Timestamp date){
        return transactionCurrencyCoefficientDao.listTransactionCurrencyCoefficientForNew(date);
    }
}
