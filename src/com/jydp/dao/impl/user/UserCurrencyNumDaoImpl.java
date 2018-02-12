package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyDO;
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
     * 根据用户id查询币种记录
     * @param userId 用户Id
     * @return 查询成功：返回用户币种记录信息，查询失败：返回null
     */
    public List<UserCurrencyDO> getUserCurrencyByUserId (int userId) {
        List<UserCurrencyDO> resultList = null;
        try {
            resultList = sqlSessionTemplate.selectList("UserCurrency_getUserCurrencyByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserCurrency_insertUserCurrencyForWeb",userCurrencyNumDOList);
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
