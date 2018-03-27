package com.jydp.service;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.StandardParameterVO;
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
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param guidancePrice  上市指导价
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean insertTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                      double buyFee, double sellFee, int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                      double guidancePrice, Timestamp upTime,Timestamp addTime);

    /**
     * 新增交易币种(后台)
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param guidancePrice  上市指导价
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean addTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                      double buyFee, double sellFee,int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                      double guidancePrice, Timestamp upTime,Timestamp addTime);

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
     * 获取所有币种信息
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
     * 根据币种简称获取交易币种
     * @param currencyShortName  货币简称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    TransactionCurrencyVO getTransactionCurrencyByCurrencyShortName(String currencyShortName);

    /**
     * 获取所有币种行情信息(web端)
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb();

    /**
     * 查询币种个数（后台）
     * @param currencyId  币种Id,查询全部填0
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    int countTransactionCurrencyForBack(int currencyId, int paymentType, int upStatus, String backAccount,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime);

    /**
     * 查询币种集合（后台）
     * @param currencyId  币种Id,查询全部填0
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
    List<TransactionCurrencyVO> listTransactionCurrencyForBack(int currencyId, int paymentType, int upStatus, String backAccount,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime, int pageNumber, int pageSize);

    /**
     * 停，复牌操作
     * @param currencyId  币种Id
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean updatePaymentType(int currencyId, int paymentType, String backerAccount, String ipAddress);

    /**
     * 上，下线币种操作
     * @param currencyId  币种Id
     * @param upStatus  上线状态,,2:上线中,4:已下线
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @param upTime  上线时间   下线填空
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean updateUpStatus(int currencyId, int upStatus, String backerAccount, String ipAddress, Timestamp upTime);

    /**
     * 查询全部币种信息
     * @return  操作成功：返回币种信息集合，操作失败：返回null
     */
    List<TransactionCurrencyDO> listTransactionCurrencyAll();

    /**
     * 根据币种id获取redis中相关币种基准参数
     * @param currencyId  币种Id
     * @return  操作成功：返回基准信息，操作失败：返回null
     */
    StandardParameterVO listTransactionCurrencyAll(int currencyId);

    /**
     * 查询全部币种信息(web端用户注册时使用)
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    List<TransactionCurrencyVO> getAllCurrencylistForWeb();

    /**
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWeb();

    /**
     * 上移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean upCurrencyRankNumber(int currencyId);

    /**
     * 下移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean downCurrencyRankNumber(int currencyId);

    /**
     * 置顶币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    boolean topCurrencyRankNumber(int currencyId);

    /**
     * 根据币种排名位置获取币种信息
     * @param rankNumber   排名位置
     * @return  操作成功：返回币种Id，操作失败：返回0
     */
    int getTransactionCurrencyByRankNumber(int rankNumber);

    /**
     * 查询所有交易币种基本信息
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    List<TransactionCurrencyBasicDTO> listAllTransactionCurrencyBasicInfor();

}
