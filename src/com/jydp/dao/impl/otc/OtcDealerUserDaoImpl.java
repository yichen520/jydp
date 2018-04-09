package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcDealerUserDao;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户标识经销商相关操作
 *
 * @author sy
 */
@Repository
public class OtcDealerUserDaoImpl implements IOtcDealerUserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 用户标识经销商
     * @param otcDealerUser 待新增的 用户标识经销商
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertOtcDealerUser(OtcDealerUserDO otcDealerUser) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("OtcDealerUser_insertOtcDealerUser", otcDealerUser);
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
