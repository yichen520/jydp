package com.jydp.service.impl.otc;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.service.IOtcTransactionUserDealService;
import com.jydp.service.IUserCurrencyNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

/**
 * 场外交易成交记录
 * @author yk
 */
@Service("otcTransactionUserDealService")
public class OtcTransactionUserDealServiceImpl implements IOtcTransactionUserDealService{

    /** 场外交易成交记录 */
    @Autowired
    private IOtcTransactionUserDealDao otcTransactionUserDealDao;

    /** 场外交易挂单记录 **/
    @Autowired
    private IOtcTransactionPendOrderService otcTransactionPendOrderService;

    /** 用户币数量 **/
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /**
     * 根据记录号查询成交记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionUserDealDO getOtcTransactionUsealByOrderNo(String orderNo) {
        return otcTransactionUserDealDao.getOtcTransactionUsealByOrderNo(orderNo);
    }

    /**
     * 用户确认收款
     * @param otcTransactionUserDeal 记录信息
     * @return 确认成功：返回true，确认失败：返回false
     */
    @Transactional
    public boolean userConfirmationOfReceipts(OtcTransactionUserDealDO otcTransactionUserDeal){
        boolean executeSuccess = false;



        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
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
    public boolean updateDealStatusByOtcOrderNo(String otcOrderNo, int dealStatus, int changedStatus,Timestamp updateTime) {
        return otcTransactionUserDealDao.updateDealStatusByOtcOrderNo(otcOrderNo,dealStatus,changedStatus,updateTime);
    }

    /**
     * 经销商回购币-确认收货
     * @param otcOrderNo  成交记录号
     * @param otcPendingOrderNo 挂单记录号
     * @param userId 操作用户Id
     * @return 修改成功：返回true; 修改失败：返回false
     */
    @Override
    public boolean dealerConfirmTakeForBuyBack(String otcOrderNo, String otcPendingOrderNo, int userId) {

        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcPendingOrderNo);

        if (otcTransactionPendOrder == null) {
            return false;
        }

        //确认收货操作非挂单经销商本人
        if (otcTransactionPendOrder.getUserId() != userId) {
            return false;
        }
        Timestamp updateTime = DateUtil.getCurrentTime();
        return otcTransactionUserDealDao.updateDealStatusByOtcOrderNo(otcOrderNo,1,2,updateTime);
    }

    /**
     * 经销商出售币-确认收款
     * @param otcTransactionUserDeal 挂单记录
     * @param userId 操作用户Id
     * @return  修改成功：返回true; 修改失败：返回false
     */
    @Override
    @Transactional
    public boolean dealerConfirmTakeForSellCoin(OtcTransactionUserDealDO otcTransactionUserDeal, int userId) {

        String otcPendingOrderNo = otcTransactionUserDeal.getOtcPendingOrderNo();
        //查询挂单记录
        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcPendingOrderNo);

        if (otcTransactionPendOrder == null) {
            return false;
        }

        //确认收款操作非挂单经销商本人
        if (otcTransactionPendOrder.getUserId() != userId) {
            return false;
        }

        //查询交易用户Id
        int transactionUserId = otcTransactionUserDeal.getUserId();
        int currencyId = otcTransactionUserDeal.getCurrencyId();
        double currencyNumber = otcTransactionUserDeal.getCurrencyNumber();
        String otcOrderNo = otcTransactionUserDeal.getOtcOrderNo();

        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(transactionUserId,currencyId);

        boolean excuteSuccess = true;
        if (userCurrencyNum == null || userCurrencyNum.getCurrencyNumberLock() < currencyNumber) {
           return false;
        }

        Timestamp updateTime = DateUtil.getCurrentTime();
        //修改成交记录状态
        excuteSuccess = otcTransactionUserDealDao.updateDealStatusByOtcOrderNo(otcOrderNo,1,2,updateTime);

        //减少用户冻结币数量
        if (excuteSuccess) {
            excuteSuccess = userCurrencyNumService.reduceCurrencyNumberLock(transactionUserId,currencyId,currencyNumber);
        }

        //增加用户货币数量
        if (excuteSuccess) {
            excuteSuccess = userCurrencyNumService.increaseCurrencyNumber(transactionUserId,currencyId,currencyNumber);
        }

        if (!excuteSuccess) {
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return excuteSuccess;
    }
}
