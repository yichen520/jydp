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
            result = sqlSessionTemplate.insert("OtcTransactionPendOrde_insertOtcTransactionPendOrder", otcTransactionPendOrderDO);
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
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo){
        OtcTransactionPendOrderDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("OtcTransactionPendOrde_getOtcTransactionPendOrderByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
