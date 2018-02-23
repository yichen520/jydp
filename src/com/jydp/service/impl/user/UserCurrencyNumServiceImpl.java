package com.jydp.service.impl.user;

import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.service.IUserCurrencyNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:26
 */
@Service("userCurrencyNumService")
public class UserCurrencyNumServiceImpl implements IUserCurrencyNumService {

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumDao userCurrencyNumDao;

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId){
        return userCurrencyNumDao.getUserCurrencyNumByUserId(userId);
    }

    /**
     * 根据currencyId查询用户币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public UserCurrencyNumDO getUserCurrencyNumByUserIdAndCurrencyId(int userId, int currencyId){
        return  userCurrencyNumDao.getUserCurrencyNumByUserIdAndCurrencyId(userId, currencyId);
    }

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList) {
        return userCurrencyNumDao.insertUserCurrencyForWeb(userCurrencyNumDOList);
    }

    /**
     * 增加用户货币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumber 增加的货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean increaseCurrencyNumber(int userId, int currencyId, double currencyNumber){
        UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
        userCurrencyNum.setUserId(userId);
        userCurrencyNum.setCurrencyId(currencyId);
        userCurrencyNum.setCurrencyNumber(currencyNumber);

        return userCurrencyNumDao.increaseCurrencyNumber(userCurrencyNum);
    }

    /**
     * 减少用户货币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumber 减少的货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean reduceCurrencyNumber(int userId, int currencyId, double currencyNumber){
        UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
        userCurrencyNum.setUserId(userId);
        userCurrencyNum.setCurrencyId(currencyId);
        userCurrencyNum.setCurrencyNumber(currencyNumber);

        return userCurrencyNumDao.reduceCurrencyNumber(userCurrencyNum);
    }

    /**
     * 增加用户货币冻结数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumberLock 增加的冻结货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean increaseCurrencyNumberLock(int userId, int currencyId, double currencyNumberLock){
        UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
        userCurrencyNum.setUserId(userId);
        userCurrencyNum.setCurrencyId(currencyId);
        userCurrencyNum.setCurrencyNumberLock(currencyNumberLock);

        return userCurrencyNumDao.increaseCurrencyNumberLock(userCurrencyNum);
    }

    /**
     * 减少用户货币冻结数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param currencyNumberLock 减少的冻结货币数量
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean reduceCurrencyNumberLock(int userId, int currencyId, double currencyNumberLock){
        UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
        userCurrencyNum.setUserId(userId);
        userCurrencyNum.setCurrencyId(currencyId);
        userCurrencyNum.setCurrencyNumberLock(currencyNumberLock);

        return userCurrencyNumDao.reduceCurrencyNumberLock(userCurrencyNum);
    }

}
