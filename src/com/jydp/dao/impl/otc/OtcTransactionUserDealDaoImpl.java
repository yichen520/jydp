package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 场外交易成交记录
 * @author yk
 */
@Repository
public class OtcTransactionUserDealDaoImpl implements IOtcTransactionUserDealDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据记录号查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionUserDealDO getOtcTransactionUsealByOrderNo(String orderNo){
        OtcTransactionUserDealDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("OtcTransactionUserDeal_getOtcTransactionUsealByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
