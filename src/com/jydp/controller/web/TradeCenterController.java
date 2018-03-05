package com.jydp.controller.web;

import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.*;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 交易中心
 * @author hz
 *
 */
@Controller
@RequestMapping("/userWeb/tradeCenter")
@Scope(value="prototype")
public class TradeCenterController {

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 匹配交易 */
    @Autowired
    private ITradeCommonService tradeCommonService;


    /** 展示 交易中心页面 */
    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request) {



        return "page/web/tradeCenter";
    }

    /** 买入 */
    @RequestMapping(value = "/buy.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO buy(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }

        //获取参数
        String buyPriceStr = StringUtil.stringNullHandle(request.getParameter("buyPrice"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String buyPwd = StringUtil.stringNullHandle(request.getParameter("buyPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        if (!StringUtil.isNotNull(buyPwd)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        double buyPrice = 0;
        if (StringUtil.isNotNull(buyPriceStr)) {
            buyPrice = Double.parseDouble(buyPriceStr);
        }

        double buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Double.parseDouble(buyNumStr);
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该用户不存在");
            return resultJson;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("没有该币种");
            return resultJson;
        }

        if(transactionCurrency.getPaymentType() != 1){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在交易状态");
            return resultJson;
        }

        if(transactionCurrency.getUpStatus() != 2){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在上线状态");
            return resultJson;
        }

        //判断交易时间限制




        //交易数量限制
        if(buyNum <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易数量不能小于等于0");
            return resultJson;
        }

        //交易价格限制
        double yesterdayLastPrice = (double)redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);

        if(transactionCurrency.getUpRange() > 0){
            double highPrice = yesterdayLastPrice * (1 + transactionCurrency.getUpRange());
            if(buyPrice > highPrice){
                resultJson.setCode(3);
                resultJson.setMessage("交易单价不符合涨幅要求");
                return resultJson;
            }
        }

        if(transactionCurrency.getDownRange() > 0){
            double lowPrice = yesterdayLastPrice * (1 - transactionCurrency.getDownRange());
            if(buyPrice < lowPrice){
                resultJson.setCode(3);
                resultJson.setMessage("交易单价不符合跌幅要求");
                return resultJson;
            }
        }

        if(buyPwd == "123456"){
            resultJson.setCode(3);
            resultJson.setMessage("支付密码不能为原始密码");
            return resultJson;
        }

        buyPwd = MD5Util.toMd5(buyPwd);
        boolean checkResult = userService.validateUserPay(user.getUserAccount(), buyPwd);
        if(!checkResult){
            resultJson.setCode(4);
            resultJson.setMessage("支付密码错误");
            return resultJson;
        }

        //计算手续费及总价
        double buyFee = transactionCurrency.getBuyFee();
        double sumPrice = NumberUtil.doubleUpFormat(buyNum * buyPrice *(1 + buyFee),8);

        if(user.getUserBalance() < sumPrice){
            resultJson.setCode(5);
            resultJson.setMessage("用户余额不足");
            return resultJson;
        }

        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 1, currencyId,
                transactionCurrency.getCurrencyName(), buyFee, buyPrice, buyNum, sumPrice);

        if(transactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("挂单失败");
            return resultJson;
        }

        //从redis判断是否可以匹配交易
        double sellOne = (double)redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId);
        if(sellOne > buyPrice){
            resultJson.setCode(1);
            resultJson.setMessage("没有可匹配的挂单");
            return resultJson;
        }

        //匹配交易
        resultJson = tradeCommonService.trade(transactionPendOrder);

        return resultJson;

    }

    /** 卖出 */
    @RequestMapping(value = "/sell.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO sell(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }

        //获取参数
        String sellPriceStr = StringUtil.stringNullHandle(request.getParameter("sellPrice"));
        String sellNumStr = StringUtil.stringNullHandle(request.getParameter("sellNum"));
        String sellPwd = StringUtil.stringNullHandle(request.getParameter("sellPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        if (!StringUtil.isNotNull(sellPwd)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        double sellPrice = 0;
        if (StringUtil.isNotNull(sellPriceStr)) {
            sellPrice = Double.parseDouble(sellPriceStr);
        }

        double sellNum = 0;
        if (StringUtil.isNotNull(sellNumStr)) {
            sellNum = Double.parseDouble(sellNumStr);
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("没有该币种");
            return resultJson;
        }

        if(transactionCurrency.getPaymentType() != 1){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在交易状态");
            return resultJson;
        }

        if(transactionCurrency.getUpStatus() != 2){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在上线状态");
            return resultJson;
        }

        //获取用户币信息
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(),
                currencyId);
        if(userCurrencyNum == null){
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        //判断交易时间限制




        //交易数量限制
        if(sellNum <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易数量不能小于等于0");
            return resultJson;
        }

        //交易价格限制
        double yesterdayLastPrice = (double)redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);

        if(transactionCurrency.getUpRange() > 0){
            double highPrice = yesterdayLastPrice * (1 + transactionCurrency.getUpRange());
            if(sellPrice > highPrice){
                resultJson.setCode(3);
                resultJson.setMessage("交易单价不符合涨幅要求");
                return resultJson;
            }
        }

        if(transactionCurrency.getDownRange() > 0){
            double lowPrice = yesterdayLastPrice * (1 - transactionCurrency.getDownRange());
            if(sellPrice < lowPrice){
                resultJson.setCode(3);
                resultJson.setMessage("交易单价不符合跌幅要求");
                return resultJson;
            }
        }

        if(sellPwd == "123456"){
            resultJson.setCode(3);
            resultJson.setMessage("支付密码不能为原始密码");
            return resultJson;
        }

        sellPwd = MD5Util.toMd5(sellPwd);
        boolean checkResult = userService.validateUserPay(user.getUserAccount(), sellPwd);
        if(!checkResult){
            resultJson.setCode(4);
            resultJson.setMessage("支付密码错误");
            return resultJson;
        }

        if(userCurrencyNum.getCurrencyNumber() < sellNum){
            resultJson.setCode(5);
            resultJson.setMessage("用户币不足");
            return resultJson;
        }

        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 2, currencyId,
                transactionCurrency.getCurrencyName(), 0, sellPrice, sellNum, 0);

        if(transactionPendOrder == null){
            resultJson.setCode(2);
            resultJson.setMessage("挂单失败");
            return resultJson;
        }

        //从redis判断是否可以匹配交易
        double buyOne = (double)redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId);
        if(buyOne < sellPrice){
            resultJson.setCode(1);
            resultJson.setMessage("没有可匹配的挂单");
            return resultJson;
        }

        //匹配交易
        resultJson = tradeCommonService.trade(transactionPendOrder);

        return resultJson;
    }

}