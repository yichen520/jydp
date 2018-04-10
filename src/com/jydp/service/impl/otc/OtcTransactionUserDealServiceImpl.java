package com.jydp.service.impl.otc;

import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 场外交易成交记录
 * @author yk
 */
@Service("otcTransactionUserDealService")
public class OtcTransactionUserDealServiceImpl implements IOtcTransactionUserDealService{

    /** 场外交易成交记录 */
    @Autowired
    private IOtcTransactionUserDealDao tcTransactionUserDealService;

    /**
     * 根据记录好查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionUserDealDO getOtcTransactionUsealBytcOrderNo(String orderNo) {
        return tcTransactionUserDealService.getOtcTransactionUsealBytcOrderNo(orderNo);
    }

    /**
     * 根据记录号修改成交记录状态
     * @param otcOrderNo 记录号
     * @param dealStatus 原成交记录状态
     * @param changedStatus 修改后的成交记录状态
     * @return 修改成功：返回true; 修改失败：返回false
     */
    @Override
    public boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus) {
        return otcTransactionUserDealDao.updateDealStatusByOtcOrderNo(otcOrderNo,dealStatus,changedStatus);
    }
}
