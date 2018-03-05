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
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param upRange  涨停幅度
     * @param downRange  跌停幅度
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                      double buyFee, double sellFee, double upRange, double downRange,
                                      int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                      Timestamp upTime,Timestamp addTime);

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
    List<TransactionCurrencyDO> getTransactionCurrencyListForWeb();

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

    /**
     * 查询币种个数（后台）
     * @param currencyName  货币名称(币种)
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    int countTransactionCurrencyForBack(String currencyName, int paymentType, int upStatus, String backAccount,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime);

    /**
     * 查询币种集合（后台）
     * @param currencyName  货币名称(币种)
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    List<TransactionCurrencyVO> listTransactionCurrencyForBack(String currencyName, int paymentType, int upStatus, String backAccount,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime, int pageNumber, int pageSize);
}
