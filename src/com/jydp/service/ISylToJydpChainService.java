package com.jydp.service;

import com.jydp.entity.DO.syl.SylToJydpChainDO;

import java.sql.Timestamp;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
public interface ISylToJydpChainService {
    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param walletOrderNo 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertSylToJydpChain(SylToJydpChainDO walletOrderNo);

    /**
     * SYL转账盛源链执行(SYL-->JYDP)
     * @param walletOrderNo syl单号
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param walletUserAccount 钱包账号
     * @param coin 转账币数量
     * @param coinType 转账币类型
     * @param orderTime 订单时间
     * @param finishTime 完成时间
     * @param currencyId 货币id
     * @param currencyName 货币名称
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean operationSylToJydpChain(String walletOrderNo, int userId, String userAccount, String walletUserAccount, double coin, String coinType,
                                    Timestamp orderTime, Timestamp finishTime,int currencyId, String currencyName);

    /**
     * 根据订单号查询订单信息
     * @param walletOrderNo 订单号
     * @param currencyId 币种
     * @return 查询成功：返回订单信息, 查询失败或者没有相关信息：返回null
     */
    SylToJydpChainDO getSylToJydpChainBysylRecordNo(String walletOrderNo, int currencyId);
}
