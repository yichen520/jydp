package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 场外交易成交记录
 * @author yk
 */
@Repository
public class OtcTransactionUserDealDaoImpl implements IOtcTransactionUserDealDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据记录号修改成交记录状态
     * @param otcOrderNo 记录号
     * @param dealStatus 原成交记录状态
     * @param changedStatus 修改后的成交记录状态
     * @return
     */
    @Override
    public boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus) {

        int result = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("otcOrderNo", otcOrderNo);
        map.put("dealStatus", dealStatus);
        map.put("changedStatus", changedStatus);

        try {
            result = sqlSessionTemplate.update("OtcTransactionUserDeal_updateDealStatusByOtcOrderNo",map);
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
