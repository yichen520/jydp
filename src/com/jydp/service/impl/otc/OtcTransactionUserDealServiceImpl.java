package com.jydp.service.impl.otc;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IOtcTransactionUserDealDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import com.jydp.entity.DTO.UserPaymentTypeDTO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.service.IOtcTransactionUserDealService;
import com.jydp.service.IUserService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.*;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;

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

    /** 用户信息 */
    @Autowired
    private IUserService serService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 用户收款记录 */
    @Autowired
    private IUserPaymentTypeService userPaymentTypeService;

    /**
     * 新增成交记录
     * @param otcPendingOrderNo 挂单记录号
     * @param userId 买方用户Id
     * @param dealerId 卖方用户Id
     * @param typeId 收款方式Id
     * @param userAccount 用户帐号
     * @param dealType 收支类型：1：买入，2：卖出，3：撤销
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param pendingRatio 挂单比例
     * @param currencyNumber 成交数量
     * @param currencyTotalPrice 成交总价
     * @param pendTime 挂单时间
     * @param paymentType 收款方式标识：1：银行卡，2：支付宝，3：微信
     * @param userPaymentType 用户收款方式
     * @return 新增成功：返回记录信息, 新增失败：返回null
     */
    @Transactional
    public JsonObjectBO insertOtcTransactionUserDeal(String otcPendingOrderNo, int userId, int dealerId, int typeId, String userAccount,
                                                     int dealType, int currencyId, String currencyName, double pendingRatio, double currencyNumber,
                                                     double currencyTotalPrice, Timestamp pendTime, int paymentType, UserPaymentTypeDTO userPaymentType){
        JsonObjectBO resultJson = new JsonObjectBO();

        Timestamp curTime = DateUtil.getCurrentTime();
        int code = 1;
        String message = "新增成功";

        UserPaymentTypeDO userPaymentTypeDO = new UserPaymentTypeDO();
        userPaymentTypeDO.setUserId(dealerId);
        userPaymentTypeDO.setOtcPendingOrderNo(otcPendingOrderNo);
        userPaymentTypeDO.setPaymentType(paymentType);
        userPaymentTypeDO.setAddTime(curTime);

        //业务执行状态
        boolean excuteSuccess = true;
        //新增用户收款方式
        if(paymentType == 1){
            userPaymentTypeDO.setPaymentAccount(userPaymentType.getPaymentAccount());
            userPaymentTypeDO.setBankName(userPaymentType.getBankName());
            userPaymentTypeDO.setBankBranch(userPaymentType.getBankBranch());
            userPaymentTypeDO.setPaymentName(userPaymentType.getPaymentName());
            userPaymentTypeDO.setPaymentPhone(userPaymentType.getPaymentPhone());

            UserPaymentTypeDO userPayment = userPaymentTypeService.insertUserPaymentType(userPaymentTypeDO);
            if(userPayment == null){
                excuteSuccess = false;
            }
            typeId = userPayment.getTypeId();
            if(!excuteSuccess){
                code = 2;
                message = "新增用户收款方式失败";
            }
        }else if(paymentType == 2 || paymentType == 3){
            userPaymentTypeDO.setPaymentAccount(userPaymentType.getPaymentAccount());
            userPaymentTypeDO.setPaymentImage(userPaymentType.getPaymentImage());

            UserPaymentTypeDO userPayment = userPaymentTypeService.insertUserPaymentType(userPaymentTypeDO);
            if(userPayment == null){
                excuteSuccess = false;
            }
            typeId = userPayment.getTypeId();
            if(!excuteSuccess){
                code = 2;
                message = "新增用户收款方式失败";
            }
        }

        String remark;
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
            otcTransactionUserDeal.setTypeId(typeId);
            otcTransactionUserDeal.setUserId(userId);
            otcTransactionUserDeal.setUserAccount(userAccount);
            otcTransactionUserDeal.setDealType(dealType);
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
                message = "增加成交记录失败";
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
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealType 交易类型（非必填）收支类型：1：买入，2：卖出，3：撤销
     * @param dealStatus 交易状态（非必填） //状态：1：待付款，2：已付款（待确认），3：已完成，4：用户取消，5：商家取消
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @return 查询成功：返回记录信息数量, 查询失败或者没有相应记录：返回0
     */
    public int numberOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealType ,int dealStatus, Timestamp startAddTime,
                                          Timestamp endAddTime){
        return otcTransactionUserDealDao.numberOtcTransactionUsealByUserId(userId, dealerName, currencyId, dealType,dealStatus, startAddTime,
                endAddTime);
    }

    /**
     * 根据用户Id查询成交记录信息
     * @param userId 用户id (必填)
     * @param dealerName 经销商名称（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    public List<OtcTransactionUserDealVO> listOtcTransactionUsealByUserId(int userId, String dealerName, int currencyId, int dealStatus, Timestamp startAddTime,
                                                                          Timestamp endAddTime, int pageNumber, int pageSize){
        return otcTransactionUserDealDao.listOtcTransactionUsealByUserId(userId, dealerName, currencyId, dealStatus, startAddTime,
                endAddTime, pageNumber, pageSize);
    }

    /**
     * 用户确认收款
     * @param otcTransactionUserDeal 记录信息
     * @return 确认成功：返回true，确认失败：返回false
     */
    @Transactional
    public boolean userConfirmationOfReceipts(OtcTransactionUserDealDO otcTransactionUserDeal){
        boolean executeSuccess = false;
        //查询委托记录
        OtcTransactionPendOrderDO otcTransactionPendOrder = otcTransactionPendOrderService.getOtcTransactionPendOrderByOrderNo(otcTransactionUserDeal.getOtcPendingOrderNo());
        if(otcTransactionPendOrder == null){
            return false;
        }

        Timestamp currentTime = DateUtil.getCurrentTime();
        //交易记录状态修改
        executeSuccess = updateDealStatusByOtcOrderNo(otcTransactionUserDeal.getOtcOrderNo(),1,3, currentTime);
        if(!executeSuccess){
            executeSuccess = updateDealStatusByOtcOrderNo(otcTransactionUserDeal.getOtcOrderNo(),2,3, currentTime);
        }

        if(!executeSuccess){
            return false;
        }


        //经销商币解冻
        executeSuccess = serService.updateAddUserAmount(otcTransactionPendOrder.getUserId(), otcTransactionUserDeal.getCurrencyNumber(), 0);
        if(executeSuccess){
            executeSuccess = serService.updateReduceUserBalanceLock(otcTransactionPendOrder.getUserId(), otcTransactionUserDeal.getCurrencyNumber());
        }

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

    /**
     * 根据经销商Id查询经销商成交记录
     * @param userId 经销商id (必填)
     * @param userAccount 用户账号（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param paymentType 收款方式 （非必填）
     * @param dealType 交易类型 （非必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    @Override
    public int countOtcTransactionUserDeallistByDealerId(int userId, String userAccount, int currencyId,
                                int dealStatus, Timestamp startAddTime, Timestamp endAddTime, int paymentType, int dealType) {
        return otcTransactionUserDealDao.countOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,
                dealStatus,startAddTime,endAddTime,paymentType,dealType);
    }

    /**
     * 根据经销商Id查询经销商成交记录
     * @param userId 经销商id (必填)
     * @param userAccount 用户账号（非必填）
     * @param currencyId 币种id（非必填）
     * @param dealStatus 交易状态（非必填）
     * @param startAddTime 申请开始时间（非必填）
     * @param endAddTime 申请结束时间（非必填）
     * @param paymentType 收款方式 （非必填）
     * @param dealType 交易类型 （非必填）
     * @param pageNumber 当前页（必填）
     * @param pageSize 每页条数（必填）
     * @return 查询成功：返回记录信息列表, 查询失败或者没有相应记录：返回null
     */
    @Override
    public List<OtcTransactionUserDealVO> getOtcTransactionUserDeallistByDealerId(int userId, String userAccount,
                                                int currencyId, int dealStatus, Timestamp startAddTime, Timestamp endAddTime, int paymentType, int dealType, int pageNumber, int pageSize) {
        return otcTransactionUserDealDao.getOtcTransactionUserDeallistByDealerId(userId,userAccount,currencyId,
                dealStatus,startAddTime,endAddTime,paymentType,dealType,pageNumber,pageSize);
    }
}
