package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endddTime 申请结束时间（非必填）
     * @return 查询成功：返回记录信息数量, 查询失败或者没有相应记录：返回0
     */
    public int numberOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                 Timestamp endddTime){
        int result = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("dealerName", dealerName);
        map.put("currencyId", currencyId);
        map.put("dealStatus", dealStatus);
        map.put("startAddTime", startAddTime);
        map.put("endddTime", endddTime);

        try {
            result = sqlSessionTemplate.selectOne("OtcTransactionUserDeal_numberOtcTransactionUsealByUserId", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endddTime 申请结束时间（非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    public List<OtcTransactionUserDealVO> listOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                          Timestamp endddTime, int pageNumber, int pageSize){
        List<OtcTransactionUserDealVO> resultList = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("dealerName", dealerName);
        map.put("currencyId", currencyId);
        map.put("dealStatus", dealStatus);
        map.put("startAddTime", startAddTime);
        map.put("endddTime", endddTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);
        try {
            resultList = sqlSessionTemplate.selectList("OtcTransactionUserDeal_listOtcTransactionUsealByUserId", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
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
