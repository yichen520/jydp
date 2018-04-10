package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionPendOrderDao;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 场外交易挂单记录相关操作
 *
 * @author lgx
 */
@Repository
public class otcTransactionPendOrderDaoImpl implements IOtcTransactionPendOrderDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 场外交易挂单记录
     * @param otcTransactionPendOrderDO 待新增的 场外交易挂单记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertOtcTransactionPendOrder(OtcTransactionPendOrderDO otcTransactionPendOrderDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("OtcDealerUser_insertOtcTransactionPendOrder", otcTransactionPendOrderDO);
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
