package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
