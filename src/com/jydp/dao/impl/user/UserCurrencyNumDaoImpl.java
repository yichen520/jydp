package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
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
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserCurrencyNum_insertUserCurrencyForWeb",userCurrencyNumDOList);
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
}
