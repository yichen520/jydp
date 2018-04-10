package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
     * 新增场外交易成交记录
     * @param otcTransactionUserDeal 场外交易成交记录
     * @return 新增成功：返回true; 新增失败：返回false
     */
    @Override
    public boolean insertOtcTransactionUserDeal(OtcTransactionUserDealDO otcTransactionUserDeal){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("OtcTransactionUserDeal_insertOtcTransactionUserDeal",otcTransactionUserDeal);
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

    /**
     * 根据记录号修改成交记录状态
     * @param otcOrderNo 记录号
     * @param dealStatus 原成交记录状态
     * @param changedStatus 修改后的成交记录状态
     * @param updateTime 修改时间
     * @return 修改成功：返回true; 修改失败：返回false
     */
    @Override
    public boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus, Timestamp updateTime) {

        int result = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("otcOrderNo", otcOrderNo);
        map.put("dealStatus", dealStatus);
        map.put("changedStatus", changedStatus);
        map.put("updateTime", updateTime);

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
