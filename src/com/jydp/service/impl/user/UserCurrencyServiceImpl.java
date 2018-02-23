package com.jydp.service.impl.user;

import com.jydp.dao.IUserCurrencyDao;
import com.jydp.entity.DO.user.UserCurrencyDO;
import com.jydp.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:19
 */
@Service("userCurrencyService")
public class UserCurrencyServiceImpl implements IUserCurrencyService {

    /** 用户货币记录 */
    @Autowired
    private IUserCurrencyDao userCurrencyDao;

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
    public boolean insertUserCurrency(String orderNo, int userId, int paymentType, String fromType, double currencyNumber,
                               int currencyId, String remark, Timestamp addTime){
        UserCurrencyDO userCurrency = new UserCurrencyDO();
        userCurrency.setOrderNo(orderNo);
        userCurrency.setUserId(userId);
        userCurrency.setPaymentType(paymentType);
        userCurrency.setFromType(fromType);
        userCurrency.setCurrencyNumber(currencyNumber);
        userCurrency.setCurrencyId(currencyId);
        userCurrency.setRemark(remark);
        userCurrency.setAddTime(addTime);

        return userCurrencyDao.insertUserCurrency(userCurrency);
    }
}
