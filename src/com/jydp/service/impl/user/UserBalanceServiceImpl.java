package com.jydp.service.impl.user;

import com.jydp.dao.UserBalanceDao;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:用户认证记录
 * Author: hht
 * Date: 2018-02-07 15:44
 */
@Service("userBalanceService")
public class UserBalanceServiceImpl implements UserBalanceService {

    /** 用户认证记录 */
    @Autowired
    private UserBalanceDao userBalanceDao;

    /**
     * 新增用户账户记录
     * @param userBalanceDO 用户账户记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUserBalance(UserBalanceDO userBalanceDO) {
        return userBalanceDao.insertUserBalance(userBalanceDO);
    }

    /**
     * 查询用户账户记录
     * @param orderNo 记录号
     * @return 查询成功：返回用户账户记录，查询失败或无数据：返回null
     */
    public UserBalanceDO getUserBalanceByOrderNo(String orderNo) {
        return userBalanceDao.getUserBalanceByOrderNo(orderNo);
    }

}
