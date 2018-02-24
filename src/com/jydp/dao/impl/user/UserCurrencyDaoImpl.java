package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserCurrencyDao;
import com.jydp.entity.DO.user.UserCurrencyDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:17
 */
@Repository
public class UserCurrencyDaoImpl implements IUserCurrencyDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户货币记录
     * @return 操作成功：返回true;操作失败：返回false
     */
    public boolean insertUserCurrency(UserCurrencyDO userCurrency){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserCurrency_insertUserCurrency",userCurrency);
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
