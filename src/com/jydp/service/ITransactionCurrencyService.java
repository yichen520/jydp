package com.jydp.service;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 交易币种
 * @author fk
 *
 */
public interface ITransactionCurrencyService {

    /**
     * 新增交易币种
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionCurrency(String currencyShortName, String currencyName, Timestamp addTime);

    /**
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    TransactionCurrencyVO getTransactionCurrencyByCurrencyId(int currencyId);

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency);

    /**
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean deleteTransactionCurrencyByCurrencyId(int currencyId);

    /**
     * 获取所有币种信息(web端用户注册时使用)
     * @return 查询成功：返回币种信息列表；查询失败：返回null
     */
    List<TransactionCurrencyVO> getTransactionCurrencyListForWeb();

    /**
     * 根据币种名称获取交易币种
     * @param currencyName  币种名称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    TransactionCurrencyVO getTransactionCurrencyByCurrencyName(String currencyName);

    /**
     * 获取所有币种行情信息(web端)
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb();
}
