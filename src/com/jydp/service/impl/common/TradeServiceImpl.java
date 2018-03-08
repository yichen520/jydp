package com.jydp.service.impl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.*;
import config.SystemAccountAmountConfig;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

/**
 * 匹配交易（单笔）
 * @author hz
 *
 */
@Service("tradeService")
public class TradeServiceImpl implements ITradeService {

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
     * 匹配交易（单笔）
     * @param order 挂单信息
     * @return JsonObjectBO 交易成功与否信息，若成功，同时返回成交后的此笔挂单信息
     */
    @Transactional
    public JsonObjectBO tradeHandle(TransactionPendOrderDO order){
        JsonObjectBO resultJson = new JsonObjectBO();
        //获取该挂单里信息
        int userId = order.getUserId();
        int currencyId = order.getCurrencyId();
        int paymentType = order.getPaymentType();
        int pendingStatus = order.getPendingStatus();

        if(pendingStatus != 1 && pendingStatus != 2){
            resultJson.setCode(4);
            resultJson.setMessage("该挂单不在交易状态");
            return resultJson;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("没有该币种");
            return resultJson;
        }
        //获取卖出手续费
        double sellFee = transactionCurrency.getSellFee();

        //获取对应的最新的挂单记录
        int matchPaymentType = 0;
        if(paymentType == 1){
            matchPaymentType = 2;
        }else if (paymentType == 2){
            matchPaymentType = 1;
        }
        TransactionPendOrderDO matchOrder = transactionPendOrderService.getLastTransactionPendOrder(0, currencyId, matchPaymentType);
        if(matchOrder == null){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
            return resultJson;
        }
        //如果匹配不上，直接返回false
        if((paymentType == 1 && order.getPendingPrice() < matchOrder.getPendingPrice()) ||
                (paymentType == 2 && order.getPendingPrice() > matchOrder.getPendingPrice())){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
            return resultJson;
        }

        //业务执行状态
        boolean excuteSuccess = true;
        //获取两挂单交易数量和最终交易数量
        double orderNum = BigDecimalUtil.sub(order.getPendingNumber(), order.getDealNumber());
        double matchOrderNum = BigDecimalUtil.sub(matchOrder.getPendingNumber(), matchOrder.getDealNumber());
        double tradeNum = Math.min(orderNum, matchOrderNum);
        //该挂单剩余可交易数量
        double restNum = BigDecimalUtil.sub(orderNum, tradeNum);

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

        double tradePrice = matchOrder.getPendingPrice(); //交易单价
        int buyUserId = 0; //买方id
        int sellUsrId = 0; //卖方id
        double buyPrice = 0; //买方挂单总价（买方出价单价*交易数量*(1+手续费)）
        double returnMoney = 0; //买方差价返还
        double buyFee = 0; //买方费率
        if(paymentType == 1){
            buyUserId = userId;
            sellUsrId = matchOrder.getUserId();
            buyFee = order.getBuyFee();
            buyPrice = NumberUtil.doubleUpFormat(BigDecimalUtil.mul(BigDecimalUtil.mul(order.getPendingPrice(), tradeNum),BigDecimalUtil.add(1,order.getBuyFee())),8);
            returnMoney = NumberUtil.doubleFormat(BigDecimalUtil.mul(BigDecimalUtil.mul((BigDecimalUtil.sub(order.getPendingPrice(), tradePrice)), tradeNum), BigDecimalUtil.add(1, order.getBuyFee())),8);
        }else if(paymentType == 2){
            buyUserId = matchOrder.getUserId();
            sellUsrId = userId;
            buyFee = matchOrder.getBuyFee();
            buyPrice = NumberUtil.doubleUpFormat(BigDecimalUtil.mul(BigDecimalUtil.mul(matchOrder.getPendingPrice(), tradeNum),BigDecimalUtil.add(1,matchOrder.getBuyFee())),8);
        }

        //成交总价
        double tradeMoney = BigDecimalUtil.mul(tradePrice, tradeNum);
        //计算卖方获得金额
        double sellMoney = NumberUtil.doubleFormat(BigDecimalUtil.mul(tradeMoney, BigDecimalUtil.sub(1, sellFee)),8);

        //减少买方用户锁定美金
        if(excuteSuccess){
            excuteSuccess = userService.updateReduceUserBalanceLock(buyUserId, buyPrice);
        }
        //增加买方用户美金
        if(returnMoney > 0){
            if(excuteSuccess){
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
        //买入订单号
        String buyOrderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(10);
        //卖出订单号
        String sellOrderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(10);
        //redis订单号
        String redisOrderNo = "";
        if(paymentType == 1){ //如果买入
            redisOrderNo = buyOrderNo;
            //增加买方成交记录
            if(excuteSuccess){
                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(buyOrderNo, order.getPendingOrderNo(),
                        buyUserId, order.getUserAccount(), 1, currencyId, order.getCurrencyName(),
                        tradePrice, tradeNum, buyFee, tradeMoney, remark, order.getAddTime(), curTime);
            }
            //增加卖方成交记录
            if(excuteSuccess){
                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(sellOrderNo, matchOrder.getPendingOrderNo(),
                        sellUsrId, matchOrder.getUserAccount(), 2, currencyId, matchOrder.getCurrencyName(),
                        tradePrice, tradeNum, sellFee, tradeMoney, remark, matchOrder.getAddTime(), curTime);
            }
        } else if(paymentType == 2){
            redisOrderNo = sellOrderNo;
            //增加买方成交记录
            if(excuteSuccess){
                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(buyOrderNo, matchOrder.getPendingOrderNo(),
                        buyUserId, matchOrder.getUserAccount(), 1, currencyId, matchOrder.getCurrencyName(),
                        tradePrice, tradeNum, buyFee, tradeMoney, remark, matchOrder.getAddTime(), curTime);
            }
            //增加卖方成交记录
            if(excuteSuccess){
                excuteSuccess = transactionUserDealService.insertTransactionUserDeal(sellOrderNo, order.getPendingOrderNo(),
                        sellUsrId, order.getUserAccount(), 2, currencyId, order.getCurrencyName(),
                        tradePrice, tradeNum, sellFee, tradeMoney, remark, order.getAddTime(), curTime);
            }
        }

        //增加redis成交记录表记录
        if (excuteSuccess) {
            excuteSuccess = transactionDealRedisService.insertTransactionDealRedis(redisOrderNo, paymentType, currencyId,
                    tradePrice, tradeNum, tradeMoney, curTime);
        }

        //增加买方账户美金记录
        if(excuteSuccess){
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "买入" + order.getCurrencyName() + "，扣除锁定美金";
            if(returnMoney > 0){
                remark = remark + ",返还差价金额";
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
            remark = "卖出" + order.getCurrencyName() + "所得美金";

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
            excuteSuccess = systemAccountAmountService.addSystemAccountAmount(SystemAccountAmountConfig.TRADE_BUY_FEE,
                    NumberUtil.doubleUpFormat(BigDecimalUtil.mul(tradeMoney, buyFee),8));
        }

        //增加系统账户记录
        if (excuteSuccess) {
            excuteSuccess = systemAccountAmountService.addSystemAccountAmount(SystemAccountAmountConfig.TRADE_SELL_FEE,
                    NumberUtil.doubleUpFormat(BigDecimalUtil.mul(tradeMoney, sellFee),8));
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        TransactionPendOrderDO restOrder = transactionPendOrderService.getPendOrderByPendingOrderNo(order.getPendingOrderNo());

        if (excuteSuccess) {
            resultJson.setCode(1);
            resultJson.setMessage("交易成功");
            resultJson.setData(JSONObject.parseObject(JSON.toJSONString(restOrder)));
            return resultJson;
        } else {
            resultJson.setCode(2);
            resultJson.setMessage("交易失败");
            return resultJson;
        }
    }

}
