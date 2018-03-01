package com.jydp.service.impl.common;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.*;
import config.SystemAccountAmountConfig;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

/**
 * 匹配交易
 * @author hz
 *
 */
@Service("tradeCommonService")
public class TradeCommonServiceImpl implements ITradeCommonService {

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 系统账户金额 */
    @Autowired
    private ISystemAccountAmountService systemAccountAmountService;

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /**
     * 匹配交易
     * @param userId 用户Id
     * @param currencyId 币种Id
     * @param paymentType 收支类型,1：买入，2：卖出
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional(propagation= Propagation.SUPPORTS)
    public boolean trade(int userId, int currencyId, int paymentType){
        //获取该用户最新的挂单记录
        TransactionPendOrderDO order = transactionPendOrderService.getLastTransactionPendOrder(userId, currencyId, paymentType);
        if(order == null){
            return false;
        }
        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            return false;
        }
        //获取买入/卖出手续费
        double buyFee = transactionCurrency.getBuyFee()/100;
        double sellFee = transactionCurrency.getSellFee()/100;

        //获取对应的最新的挂单记录
        int matchPaymentType = 0;
        if(paymentType == 1){
            matchPaymentType = 2;
        }else if (paymentType == 2){
            matchPaymentType = 1;
        }
        TransactionPendOrderDO matchOrder = transactionPendOrderService.getLastTransactionPendOrder(0, currencyId, matchPaymentType);
        if(matchOrder == null){
            return false;
        }
        //如果匹配不上，直接返回false
        if((paymentType == 1 && order.getPendingPrice() < matchOrder.getPendingPrice()) ||
                (paymentType == 2 && order.getPendingPrice() > matchOrder.getPendingPrice())){
            return false;
        }

        //业务执行状态
        boolean excuteSuccess = true;
        //获取两挂单交易数量和最终交易数量
        double orderNum = order.getPendingNumber() - order.getDealNumber();
        double matchOrderNum = matchOrder.getPendingNumber() - matchOrder.getDealNumber();
        double tradeNum = Math.min(orderNum, matchOrderNum);

        //修改两个订单的交易数量和状态
        Timestamp curTime = DateUtil.getCurrentTime();
        if(tradeNum == orderNum && orderNum != matchOrderNum){
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updateAllDeal(order.getPendingOrderNo(), tradeNum, curTime);
            }
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updatePartDeal(matchOrder.getPendingOrderNo(), tradeNum, curTime);
            }
        }else if (tradeNum == matchOrderNum && orderNum != matchOrderNum){
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updateAllDeal(matchOrder.getPendingOrderNo(), tradeNum, curTime);
            }
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updatePartDeal(order.getPendingOrderNo(), tradeNum, curTime);
            }
        }else if (orderNum == matchOrderNum){
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updateAllDeal(matchOrder.getPendingOrderNo(), tradeNum, curTime);
            }
            if(excuteSuccess){
                excuteSuccess = transactionPendOrderService.updateAllDeal(order.getPendingOrderNo(), tradeNum, curTime);
            }
        }

        double tradePrice = 0; //交易单价
        int buyUserId = 0; //买方id
        int sellUsrId = 0; //卖方id
        double returnMoney = 0; //差价返还金额
        double buyPrice = 0; //买方挂单总价（买方出价单价*交易数量*1.0002）
        if(paymentType == 1){
            tradePrice = matchOrder.getPendingPrice();
            buyUserId = userId;
            sellUsrId = matchOrder.getUserId();
            returnMoney = (order.getPendingPrice() - tradePrice) * tradeNum * (1 + buyFee);
            buyPrice = order.getPendingPrice() * tradeNum * (1 + buyFee);
        }else if(paymentType == 2){
            tradePrice = order.getPendingPrice();
            buyUserId = matchOrder.getUserId();
            sellUsrId = userId;
            returnMoney = (matchOrder.getPendingPrice() - tradePrice) * tradeNum * (1 + buyFee);
            buyPrice = matchOrder.getPendingPrice() * tradeNum * (1 + buyFee);
        }

        //成交总价
        double tradeMoney = tradePrice * tradeNum;
        //计算买方消费金额
        double buyMoney = tradeMoney * (1 + buyFee);
        //计算卖方获得金额
        double sellMoney = tradeMoney * (1 - sellFee);

        //减少买方用户锁定美金
        if(excuteSuccess){
            excuteSuccess = userService.updateReduceUserBalanceLock(buyUserId, returnMoney + buyMoney);
        }
        //增加买方用户美金
        if(excuteSuccess){
            if(returnMoney > 0){
                excuteSuccess = userService.updateAddUserAmount(buyUserId, returnMoney, 0);
            }
        }
        //增加买方币数量
        if(excuteSuccess){
            excuteSuccess = userCurrencyNumService.increaseCurrencyNumber(buyUserId, currencyId, tradeNum);
        }

        //减少卖方用户锁定币数量
        if(excuteSuccess){
            excuteSuccess = userCurrencyNumService.reduceCurrencyNumberLock(sellUsrId, currencyId, tradeNum);
        }
        //增加卖方用户美金
        if(excuteSuccess){
            excuteSuccess = userService.updateAddUserAmount(sellUsrId, sellMoney, 0);
        }

        String  remark = "挂单成交";
        if(paymentType == 1){ //如果买入
            //增加买方成交记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(orderNo, order.getPendingOrderNo(),
                        userId, order.getUserAccount(), 1, currencyId, order.getCurrencyName(),
                        tradePrice, tradeNum, tradeMoney, remark, order.getAddTime(), curTime);
                //增加redis成交记录表记录
                if (excuteSuccess) {
                    excuteSuccess = transactionDealRedisService.insertTransactionDealRedis(orderNo, paymentType, currencyId,
                            tradePrice, tradeNum, tradeMoney, curTime);
                }
            }
            //增加卖方成交记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(orderNo, matchOrder.getPendingOrderNo(),
                        matchOrder.getUserId(), matchOrder.getUserAccount(), 2, currencyId, matchOrder.getCurrencyName(),
                        tradePrice, tradeNum, tradeMoney, remark, matchOrder.getAddTime(), curTime);
            }
        } else if(paymentType == 2){
            //增加买方成交记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(orderNo, matchOrder.getPendingOrderNo(),
                        buyUserId, matchOrder.getUserAccount(), 1, currencyId, matchOrder.getCurrencyName(),
                        tradePrice, tradeNum, tradeMoney, remark, matchOrder.getAddTime(), curTime);
            }
            //增加卖方成交记录
            if(excuteSuccess){
                String orderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(orderNo, order.getPendingOrderNo(),
                        sellUsrId, order.getUserAccount(), 2, currencyId, order.getCurrencyName(),
                        tradePrice, tradeNum, tradeMoney, remark, order.getAddTime(), curTime);
                //增加redis成交记录表记录
                if (excuteSuccess) {
                    excuteSuccess = transactionDealRedisService.insertTransactionDealRedis(orderNo, paymentType, currencyId,
                            tradePrice, tradeNum, tradeMoney, curTime);
                }
            }
        }

        //增加买方账户美金记录
        if(excuteSuccess){
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "买入" + order.getCurrencyName() + "，扣除锁定美金及手续费";
            if(returnMoney > 0){
                remark = "买入" + order.getCurrencyName() + "，扣除锁定美金及手续费,返还差价金额";
            }

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(buyUserId);
            userBalance.setCurrencyId(UserBalanceConfig.DOLLAR_ID);
            userBalance.setCurrencyName(UserBalanceConfig.DOLLAR);
            userBalance.setFromType(UserBalanceConfig.PEND_SUCCESS);
            userBalance.setBalanceNumber(returnMoney);
            userBalance.setFrozenNumber(-buyPrice);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);

            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }
        //增加买方币记录
        if(excuteSuccess){
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "交易成功获得币";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(buyUserId);
            userBalance.setCurrencyId(currencyId);
            userBalance.setCurrencyName(order.getCurrencyName());
            userBalance.setFromType(UserBalanceConfig.PEND_SUCCESS);
            userBalance.setBalanceNumber(tradeNum);
            userBalance.setFrozenNumber(0);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);

            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }

        //增加卖方账户美金记录
        if(excuteSuccess){
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "卖出" + order.getCurrencyName() + "所得美金，扣除手续费";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(sellUsrId);
            userBalance.setCurrencyId(UserBalanceConfig.DOLLAR_ID);
            userBalance.setCurrencyName(UserBalanceConfig.DOLLAR);
            userBalance.setFromType(UserBalanceConfig.PEND_SUCCESS);
            userBalance.setBalanceNumber(sellMoney);
            userBalance.setFrozenNumber(0);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);

            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }
        //增加卖方币记录
        if(excuteSuccess){
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "交易成功扣除锁定币";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(sellUsrId);
            userBalance.setCurrencyId(currencyId);
            userBalance.setCurrencyName(matchOrder.getCurrencyName());
            userBalance.setFromType(UserBalanceConfig.PEND_SUCCESS);
            userBalance.setBalanceNumber(0);
            userBalance.setFrozenNumber(-tradeNum);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);

            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }

        //增加系统账户记录
        if (excuteSuccess) {
            excuteSuccess = systemAccountAmountService.addSystemAccountAmount(SystemAccountAmountConfig.TRADE_BUY_FEE, tradeMoney*0.002);
        }
        if (excuteSuccess) {
            excuteSuccess = systemAccountAmountService.addSystemAccountAmount(SystemAccountAmountConfig.TRADE_SELL_FEE,tradeMoney*0.002);
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return excuteSuccess;
    }

}
