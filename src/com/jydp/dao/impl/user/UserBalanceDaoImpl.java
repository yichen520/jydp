package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.UserBalanceDao;
import com.jydp.entity.DO.user.UserBalanceDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:用户账户记录
 * Author: hht
 * Date: 2018-02-07 15:33
 */
@Repository
public class UserBalanceDaoImpl implements UserBalanceDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户账户记录
     * @param userBalanceDO 用户账户记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUserBalance(UserBalanceDO userBalanceDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserBalance_insertUserBalance", userBalanceDO);
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
     * 查询用户账户记录
     * @param orderNo 记录号
     * @return 查询成功：返回用户账户记录，查询失败或无数据：返回null
     */
    public UserBalanceDO getUserBalanceByOrderNo(String orderNo) {
        UserBalanceDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserBalance_getUserBalanceByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }
}
