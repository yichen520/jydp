package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserPaymentTypeDao;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户标识经销商相关操作
 *
 * @author lgx
 */
@Repository
public class UserPaymentTypeDaoImpl implements IUserPaymentTypeDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 收款方式
     *
     * @param userPaymentType 待新增的 收款方式
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertUserPaymentType(UserPaymentTypeDO userPaymentType) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserPaymentType_insertUserPaymentType", userPaymentType);
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
