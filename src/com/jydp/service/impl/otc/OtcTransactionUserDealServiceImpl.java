package com.jydp.service.impl.otc;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.service.IOtcTransactionUserDealService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.*;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
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

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /**
     * 新增成交记录
     * @param otcPendingOrderNo 挂单记录号
     * @param userId 买方用户Id
     * @param dealerId 卖方用户Id
     * @param typeId 收款方式Id
     * @param userAccount 用户帐号
     * @param paymentType 收支类型：1：买入，2：卖出，3：撤销
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param pendingRatio 挂单比例
     * @param currencyNumber 成交数量
     * @param currencyTotalPrice 成交总价
     * @param pendTime 挂单时间
     * @return 新增成功：返回记录信息, 新增失败：返回null
     */
    @Transactional
    public JsonObjectBO insertOtcTransactionUserDeal(String otcPendingOrderNo, int userId, int dealerId, int typeId, String userAccount,
                                                     int paymentType, int currencyId, String currencyName, double pendingRatio, double currencyNumber,
                                                     double currencyTotalPrice, Timestamp pendTime){
        JsonObjectBO resultJson = new JsonObjectBO();
        //业务执行状态
        boolean excuteSuccess = true;
        Timestamp curTime = DateUtil.getCurrentTime();
        String remark;
        int code = 1;
        String message = "新增成功";
        if(currencyId == 999){
            //增加买方用户冻结XT
            if(excuteSuccess){
                excuteSuccess = userService.updateAddUserAmount(userId, 0, currencyNumber);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加买方用户冻结XT失败";
                }
            }
            //减少卖方用户XT
            if(excuteSuccess){
                excuteSuccess = userService.updateReduceUserBalance(dealerId, currencyNumber);
                if(!excuteSuccess){
                    code = 2;
                    message = "减少卖方用户XT失败";
                }
            }
            //增加买方账户XT记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.USER_BALANCE +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);
                remark = "线下买入" + currencyName + "广告，增加锁定XT";

                UserBalanceDO userBalance = new UserBalanceDO();
                userBalance.setOrderNo(orderNo);
                userBalance.setUserId(userId);
                userBalance.setCurrencyId(UserBalanceConfig.DOLLAR_ID);
                userBalance.setCurrencyName(UserBalanceConfig.DOLLAR);
                userBalance.setFromType(UserBalanceConfig.BUY_OFFLINE_AD);
                userBalance.setBalanceNumber(0);
                userBalance.setFrozenNumber(currencyNumber);
                userBalance.setRemark(remark);
                userBalance.setAddTime(curTime);

                excuteSuccess = userBalanceService.insertUserBalance(userBalance);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加买方账户XT记录失败";
                }
            }
            //增加卖方账户XT记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.USER_BALANCE +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);
                remark = "线下卖出" + currencyName + "广告，扣除XT";

                UserBalanceDO userBalance = new UserBalanceDO();
                userBalance.setOrderNo(orderNo);
                userBalance.setUserId(dealerId);
                userBalance.setCurrencyId(UserBalanceConfig.DOLLAR_ID);
                userBalance.setCurrencyName(UserBalanceConfig.DOLLAR);
                userBalance.setFromType(UserBalanceConfig.SELL_OFFLINE_AD);
                userBalance.setBalanceNumber(-currencyNumber);
                userBalance.setFrozenNumber(0);
                userBalance.setRemark(remark);
                userBalance.setAddTime(curTime);

                excuteSuccess = userBalanceService.insertUserBalance(userBalance);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加卖方账户XT记录失败";
                }
            }

        }else {
            //增加买方冻结币数量
            if(excuteSuccess){
                excuteSuccess = userCurrencyNumService.increaseCurrencyNumberLock(userId, currencyId, currencyNumber);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加买方用户冻结XT失败";
                }
            }
            //减少卖方用户币数量
            if(excuteSuccess){
                excuteSuccess = userCurrencyNumService.reduceCurrencyNumber(dealerId, currencyId, currencyNumber);
                if(!excuteSuccess){
                    code = 2;
                    message = "减少卖方用户XT失败";
                }
            }
            //增加买方币记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.USER_BALANCE +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);
                remark = "线下买入" + currencyName + "广告,增加锁定" + currencyName;

                UserBalanceDO userBalance = new UserBalanceDO();
                userBalance.setOrderNo(orderNo);
                userBalance.setUserId(userId);
                userBalance.setCurrencyId(currencyId);
                userBalance.setCurrencyName(currencyName);
                userBalance.setFromType(UserBalanceConfig.BUY_OFFLINE_AD);
                userBalance.setBalanceNumber(0);
                userBalance.setFrozenNumber(currencyNumber);
                userBalance.setRemark(remark);
                userBalance.setAddTime(curTime);

                excuteSuccess = userBalanceService.insertUserBalance(userBalance);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加买方账户XT记录失败";
                }
            }
            //增加卖方币记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.USER_BALANCE +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);
                remark = "线下卖出" + currencyName + "广告，扣除" + currencyName;

                UserBalanceDO userBalance = new UserBalanceDO();
                userBalance.setOrderNo(orderNo);
                userBalance.setUserId(dealerId);
                userBalance.setCurrencyId(currencyId);
                userBalance.setCurrencyName(currencyName);
                userBalance.setFromType(UserBalanceConfig.SELL_OFFLINE_AD);
                userBalance.setBalanceNumber(-currencyNumber);
                userBalance.setFrozenNumber(0);
                userBalance.setRemark(remark);
                userBalance.setAddTime(curTime);

                excuteSuccess = userBalanceService.insertUserBalance(userBalance);
                if(!excuteSuccess){
                    code = 2;
                    message = "增加卖方账户XT记录失败";
                }
            }
        }

        //新增成交记录
        if(excuteSuccess){
            OtcTransactionUserDealDO otcTransactionUserDeal = new OtcTransactionUserDealDO();
            String otcOrderNo = SystemCommonConfig.TRANSACTION_OTC_USER_DEAL +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            otcTransactionUserDeal.setOtcOrderNo(otcOrderNo);
            otcTransactionUserDeal.setOtcPendingOrderNo(otcPendingOrderNo);
            otcTransactionUserDeal.setUserId(userId);
            otcTransactionUserDeal.setUserAccount(userAccount);
            otcTransactionUserDeal.setPaymentType(paymentType);
            otcTransactionUserDeal.setCurrencyId(currencyId);
            otcTransactionUserDeal.setCurrencyName(currencyName);
            otcTransactionUserDeal.setPendingRatio(pendingRatio);
            otcTransactionUserDeal.setCurrencyNumber(currencyNumber);
            otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
            otcTransactionUserDeal.setPendTime(pendTime);
            otcTransactionUserDeal.setDealStatus(1);
            otcTransactionUserDeal.setAddTime(curTime);


            excuteSuccess = otcTransactionUserDealDao.insertOtcTransactionUserDeal(otcTransactionUserDeal);
            if(!excuteSuccess){
                code = 2;
                message = "增加卖方账户XT记录失败";
            }
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        resultJson.setCode(code);
        resultJson.setMessage(message);
        return  resultJson;
    }

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
