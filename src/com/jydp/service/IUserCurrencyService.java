package com.jydp.service;

import java.sql.Timestamp;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:18
 */
public interface IUserCurrencyService {

    /**
     * 新增用户货币记录
     * @param orderNo 记录号：业务类型（2）+日期（6）+随机位（10）
     * @param userId 用户Id
     * @param paymentType 收支类型：1：增加，2：减少
     * @param fromType 账户来源
     * @param currencyNumber 货币数量
     * @param currencyId 币种Id
     * @param remark 备注：手续费
     * @param addTime 添加时间
     * @return 操作成功：返回true;操作失败：返回false
     */
    boolean insertUserCurrency(String orderNo, int userId, int paymentType, String fromType, double currencyNumber,
                               int currencyId, String remark, Timestamp addTime);

}
