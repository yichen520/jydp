package com.jydp.service.impl.user;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.service.IUserCurrencyNumService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * 新增用户币账户
     * @param userCurrencyNum 用户币账户
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertUserCurrencyNum(UserCurrencyNumDO userCurrencyNum) {
        return userCurrencyNumDao.insertUserCurrencyNum(userCurrencyNum);
    }

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId){
        return userCurrencyNumDao.getUserCurrencyNumByUserId(userId);
    }

    /**
     * 查询用户币数量，带货币名称
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForBacker (int userId) {
        return userCurrencyNumDao.getUserCurrencyNumByUserIdForBacker(userId);
    }

    /**
     * 查询用户币数量，带货币名称带货币名称(用户端)
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForWeb (int userId){
        return userCurrencyNumDao.getUserCurrencyNumByUserIdForWeb(userId);
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
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumList) {
        return userCurrencyNumDao.insertUserCurrencyForWeb(userCurrencyNumList);
    }

    /**
     * wap端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWap(List<UserCurrencyNumDO> userCurrencyNumList) {
        return userCurrencyNumDao.insertUserCurrencyForWap(userCurrencyNumList);
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

    /**
     * 查询用户币种账户错误总数（定时器对账操作）
     * @param checkAmount 可用资产最大差额（数字货币）
     * @param checkAmountLock 锁定资产最大差额（数字货币）
     * @return 查询成功：返回用户币种账户错误总数，查询失败：返回0
     */
    public int countCheckUserAmountForTimer(double checkAmount, double checkAmountLock) {
        return userCurrencyNumDao.countCheckUserAmountForTimer(checkAmount, checkAmountLock);
    }

    /**
     * 查询用户币种账户错误列表信息（定时器对账操作）
     * @param checkAmount 可用资产最大差额（数字货币）
     * @param checkAmountLock 锁定资产最大差额（数字货币）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功：返回用户币种账户错误列表信息，查询失败：返回null
     */
    public List<UserAmountCheckDTO> listCheckUserAmountForTimer(double checkAmount, double checkAmountLock,
                                                         int pageNumber, int pageSize) {
        return userCurrencyNumDao.listCheckUserAmountForTimer(checkAmount, checkAmountLock, pageNumber, pageSize);
    }
}
