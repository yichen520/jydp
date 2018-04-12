package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DTO.BackerUserCurrencyNumDTO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
@Repository
public class UserCurrencyNumDaoImpl implements IUserCurrencyNumDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户币账户
     * @param userCurrencyNum 用户币账户
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertUserCurrencyNum(UserCurrencyNumDO userCurrencyNum) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("UserCurrencyNum_insertUserCurrencyNum", userCurrencyNum);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询用户币数量
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<UserCurrencyNumDO> getUserCurrencyNumByUserId (int userId) {
        List<UserCurrencyNumDO> resultList = null;
        try {
            resultList = sqlSessionTemplate.selectList("UserCurrencyNum_getUserCurrencyNumByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * 查询用户币数量，带货币名称
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForBacker (int userId) {
        List<BackerUserCurrencyNumDTO> resultList = null;
        try {
            resultList = sqlSessionTemplate.selectList("UserCurrencyNum_getUserCurrencyNumByUserIdForBacker", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * 查询用户币数量，带货币名称(用户端)
     * @param userId 用户Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public List<BackerUserCurrencyNumDTO> getUserCurrencyNumByUserIdForWeb (int userId) {
        List<BackerUserCurrencyNumDTO> resultList = null;
        try {
            resultList = sqlSessionTemplate.selectList("UserCurrencyNum_getUserCurrencyNumByUserIdForWeb", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * 根据currencyId查询用户币数量
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @return 查询成功：返回用户币数量，查询失败：返回null
     */
    public UserCurrencyNumDO getUserCurrencyNumByUserIdAndCurrencyId(int userId, int currencyId){
        UserCurrencyNumDO userCurrencyNum = null;

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("currencyId",currencyId);

        try {
            userCurrencyNum = sqlSessionTemplate.selectOne("UserCurrencyNum_getUserCurrencyNumByUserIdAndCurrencyId", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return userCurrencyNum;
    }

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumList) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserCurrencyNum_insertUserCurrencyForWeb",userCurrencyNumList);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * wap端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWap(List<UserCurrencyNumDO> userCurrencyNumList) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("UserCurrencyNum_insertUserCurrencyForWap",userCurrencyNumList);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 增加用户货币数量
     * @param userCurrencyNum 增加信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean increaseCurrencyNumber(UserCurrencyNumDO userCurrencyNum){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("UserCurrencyNum_increaseCurrencyNumber",userCurrencyNum);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 减少用户货币数量
     * @param userCurrencyNum 减少信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean reduceCurrencyNumber(UserCurrencyNumDO userCurrencyNum){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("UserCurrencyNum_reduceCurrencyNumber",userCurrencyNum);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 增加用户货币冻结数量
     * @param userCurrencyNum 增加信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean increaseCurrencyNumberLock(UserCurrencyNumDO userCurrencyNum){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("UserCurrencyNum_increaseCurrencyNumberLock",userCurrencyNum);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 减少用户货币冻结数量
     * @param userCurrencyNum 减少信息
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean reduceCurrencyNumberLock(UserCurrencyNumDO userCurrencyNum){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("UserCurrencyNum_reduceCurrencyNumberLock",userCurrencyNum);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询用户币种账户错误总数（定时器对账操作）
     * @param checkAmount 可用资产最大差额（数字货币）
     * @param checkAmountLock 锁定资产最大差额（数字货币）
     * @return 查询成功：返回用户币种账户错误总数，查询失败：返回0
     */
    public int countCheckUserAmountForTimer(double checkAmount, double checkAmountLock) {
        Map<String, Object> map = new HashMap<>();
        map.put("checkAmount", checkAmount);
        map.put("checkAmountLock", checkAmountLock);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("UserCurrencyNum_countCheckUserAmountForTimer", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return  result;
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
        Map<String, Object> map = new HashMap<>();
        map.put("checkAmount", checkAmount);
        map.put("checkAmountLock", checkAmountLock);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserAmountCheckDTO> result = null;
        try {
            result = sqlSessionTemplate.selectList("UserCurrencyNum_listCheckUserAmountForTimer", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户币数量,(上线中,停牌的状态)
     * @param userId 用户Id
     * @return 查询成功:返回用户币数量集合, 查询失败:返回null
     */
    public List<UserCurrencyNumDO> listUserCurrencyNumByUserId(int userId) {
        List<UserCurrencyNumDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("UserCurrencyNum_listUserCurrencyNumByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

}
