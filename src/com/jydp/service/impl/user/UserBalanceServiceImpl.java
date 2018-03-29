package com.jydp.service.impl.user;

import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IUserBalanceDao;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.IUserBalanceService;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:用户账户记录
 * Author: hht
 * Date: 2018-02-07 15:44
 */
@Service("userBalanceService")
public class UserBalanceServiceImpl implements IUserBalanceService {

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceDao userBalanceDao;

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

    /**
     * 根据用户Id查询用户账户记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户账户记录列表；查询失败：返回null
     */
    @Override
    public List<UserBalanceDO> getUserBalancelistForWeb(int userId, int pageNumber, int pageSize) {

        List<UserBalanceDO> userBalanceList = userBalanceDao.getUserBalancelistForWeb(userId, pageNumber, pageSize);

        if (userBalanceList != null) {
            for (UserBalanceDO userBalance:userBalanceList) {
                int currencyId = userBalance.getCurrencyId();
                int accuracy = 6;
                if (currencyId == UserBalanceConfig.DOLLAR_ID) {
                    accuracy = 2;
                }
                userBalance.setBalanceNumber(NumberUtil.doubleFormat(userBalance.getBalanceNumber(),accuracy));
                userBalance.setFrozenNumber(NumberUtil.doubleFormat(userBalance.getFrozenNumber(),accuracy));
            }
        }
        return userBalanceList;
    }

    /**
     * 根据userId查询用户记录总数(web端)
     * @param userId 用户Id
     * @return 查询成功：返回用户账户记录总数；查询失败：返回0
     */
    @Override
    public int countUserBalanceForWeb(int userId) {
        return userBalanceDao.countUserBalanceForWeb(userId);
    }

    /**
     * 根据userBalanceList批量插入用户记录
     * @param userBalanceList 用户记录集合
     * @return 成功：true；查询失败：false
     */
    public boolean insertUserBalanceList(List<UserBalanceDO>userBalanceList){
        return userBalanceDao.insertUserBalanceList(userBalanceList);
    }

}
