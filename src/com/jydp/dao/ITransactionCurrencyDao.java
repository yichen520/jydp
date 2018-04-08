package com.jydp.dao;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.KGraphCurrencyDTO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 交易币种
 * @author fk
 *
 */
public interface ITransactionCurrencyDao {

    /**
     * 新增交易币种
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    int insertTransactionCurrency(TransactionCurrencyDO transactionCurrency);

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
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
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
     * 为币种行情获取所有币种信息
     * @return 查询成功：返回各币种信息；查询失败：返回null
     */
    List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb();

    /**
     * 为币种行情获取所有币种信息(wap)
     * @return 查询成功：返回各币种信息；查询失败：返回null
     */
    List<TransactionUserDealDTO> getTransactionCurrencyMarketForWap();

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
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWap();

    /**
     * 根据币种排名位置获取币种信息id
     * @param rankNumber   排名位置
     * @return  操作成功：返回币种Id，操作失败：返回0
     */
    int getTransactionCurrencyByRankNumber(int rankNumber);

    /**
     * 修改币种信息排名（大于该排名的所有币种排名-1）
     * @param rankNumber 排名位置
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateCurrencyRankNumber(int rankNumber);

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
     * 查询所有交易币种基本信息
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    List<TransactionCurrencyBasicDTO> listAllTransactionCurrencyBasicInfor();

    /**
     * 获取所有上线中和停牌的币种id集合
     * @return 查询成功:返回币种id集合, 查询失败:返回null
     */
    List<Integer> listcurrencyId();
    /**
     * 查询所有交易币种id,和上线状态（k线图统计操作）
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    List<KGraphCurrencyDTO> listKGraphCurrency();

    /**
     * 获取币种交易状态（1:待上线,2:上线中,3:停牌,4:已下线）
     * @param currencyId  币种Id
     * @return  操作成功：返回币种交易状态，操作失败：返回0
     */
    int getCurrencyUpstatusByCurrencyId(int currencyId);
}
