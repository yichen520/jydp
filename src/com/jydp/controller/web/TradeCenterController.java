package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.StandardParameterVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.TransactionPendOrderVO;
import com.jydp.entity.VO.UserDealCapitalMessageVO;
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
import java.util.List;

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
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();
        List<TransactionPendOrderVO> transactionPendOrderList = null;

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        if (!StringUtil.isNotNull(currencyIdStr)) {
            List<TransactionCurrencyVO> transactionCurrency = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
            if(transactionCurrency == null || transactionCurrency.size() <= 0 ){
                return "redirect:/userWeb/homePage/show";
            }
            currencyIdStr = transactionCurrency.get(0).getCurrencyId() + "";
        }

        int currencyId = Integer.parseInt(currencyIdStr);

        //获取用户交易中心相关资产信息
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user != null) {
            userDealCapitalMessage = userService.countCheckUserAmountForTimer(user.getUserId(), currencyId);
            transactionPendOrderList = transactionPendOrderService.listPendOrderForWeb(user.getUserId(),currencyId,0, 10);
        }

        //获取币种信息
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency != null){
            transactionCurrency.setBuyFee(transactionCurrency.getBuyFee() * 100);
            transactionCurrency.setSellFee(transactionCurrency.getSellFee() * 100);
        }
        //获取币种基准信息
        StandardParameterVO standardParameter = transactionCurrencyService.listTransactionCurrencyAll(currencyId);
        //获取成交记录
        List<TransactionDealRedisDO> dealList = null;
        dealList = (List<TransactionDealRedisDO>) redisService.getValue(RedisKeyConfig.CURRENCY_DEAL_KEY + transactionCurrency.getCurrencyId());
        if (dealList == null || dealList.isEmpty()) {
            dealList = transactionDealRedisService.listTransactionDealRedis(50, transactionCurrency.getCurrencyId());
        }
        //获取挂单记录
        List<TransactionPendOrderDTO> transactionPendOrderBuyList = null;
        transactionPendOrderBuyList = (List<TransactionPendOrderDTO>) redisService.getValue(RedisKeyConfig.BUY_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderBuyList == null || transactionPendOrderBuyList.isEmpty()) {
            transactionPendOrderBuyList = transactionPendOrderService.listLatestRecords(1,transactionCurrency.getCurrencyId(),15);
        }

        List<TransactionPendOrderDTO> transactionPendOrderSellList = null;
        transactionPendOrderSellList = (List<TransactionPendOrderDTO>) redisService.getValue(RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderSellList == null || transactionPendOrderSellList.isEmpty()) {
            transactionPendOrderSellList = transactionPendOrderService.listLatestRecords(2,transactionCurrency.getCurrencyId(),15);
        }

        request.setAttribute("transactionPendOrderBuyList", transactionPendOrderBuyList);
        request.setAttribute("transactionPendOrderSellList", transactionPendOrderSellList);
        request.setAttribute("dealList", dealList);

        request.setAttribute("transactionPendOrderList", transactionPendOrderList);
        request.setAttribute("transactionCurrency", transactionCurrency);
        request.setAttribute("standardParameter", standardParameter);
        request.setAttribute("userDealCapitalMessage", userDealCapitalMessage);
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

        if(user.getAccountStatus() != 1){
            resultJson.setCode(3);
            resultJson.setMessage("该账号已被禁用");
            return resultJson;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("币种信息获取失败,请稍候再试");
            return resultJson;
        }

        if(transactionCurrency.getUpStatus() != 2){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在上线状态");
            return resultJson;
        }

        if(transactionCurrency.getPaymentType() != 1){
            resultJson.setCode(4);
            resultJson.setMessage("该币种不在交易状态");
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
        if(buyPrice <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易单价不能小于等于0");
            return resultJson;
        }

        Object yesterdayPrice = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);
        if(yesterdayPrice != "" && yesterdayPrice != null){
            double yesterdayLastPrice = (double)yesterdayPrice;
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
        }

        String pwd = MD5Util.toMd5("123456");
        boolean resultBoo = userService.validateUserPay(user.getUserAccount(), pwd);
        if(resultBoo){
            resultJson.setCode(3);
            resultJson.setMessage("支付密码不能为原始密码,请修改后操作");
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
        Object sellOneOb = redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId);
        double sellOne = 0;
        if(sellOneOb != null && sellOneOb != ""){
            sellOne = (double)sellOneOb;
        }

        if(sellOne > buyPrice || sellOne == 0){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
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

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
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

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if(user == null){
            resultJson.setCode(3);
            resultJson.setMessage("该用户不存在");
            return resultJson;
        }

        if(user.getAccountStatus() != 1){
            resultJson.setCode(3);
            resultJson.setMessage("该账号已被禁用");
            return resultJson;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency == null){
            resultJson.setCode(3);
            resultJson.setMessage("币种信息获取失败,请稍候再试");
            return resultJson;
        }

        if(transactionCurrency.getUpStatus() != 2){
            resultJson.setCode(5);
            resultJson.setMessage("该币种不在上线状态");
            return resultJson;
        }

        if(transactionCurrency.getPaymentType() != 1){
            resultJson.setCode(4);
            resultJson.setMessage("该币种不在交易状态");
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
        if(sellPrice <= 0){
            resultJson.setCode(3);
            resultJson.setMessage("交易单价不能小于等于0");
            return resultJson;
        }

        Object yesterdayPrice = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);
        if(yesterdayPrice != "" && yesterdayPrice != null){
            double yesterdayLastPrice = (double)yesterdayPrice;
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
        }

        String pwd = MD5Util.toMd5("123456");
        boolean resultBoo = userService.validateUserPay(user.getUserAccount(), pwd);
        if(resultBoo){
            resultJson.setCode(3);
            resultJson.setMessage("支付密码不能为原始密码,请修改后操作");
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
        Object buyOneOb = redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId);
        double buyOne = 0;
        if(buyOneOb != null && buyOneOb != ""){
            buyOne = (double)buyOneOb;
        }

        if(buyOne < sellPrice){
            resultJson.setCode(1);
            resultJson.setMessage("挂单成功");
            return resultJson;
        }

        //匹配交易
        resultJson = tradeCommonService.trade(transactionPendOrder);

        return resultJson;
    }


    /** 获取成交记录 */
    @RequestMapping(value = "/deal", method = RequestMethod.POST)
    public @ResponseBody  JsonObjectBO deal(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();

        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        if (!StringUtil.isNotNull(currencyIdStr)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency != null){

            if(transactionCurrency.getUpStatus() == 4){
                resultJson.setCode(5);
                resultJson.setMessage("该币种不在上线状态");
                return resultJson;
            }
        }

        List<TransactionDealRedisDO> dealList = null;
        dealList = (List<TransactionDealRedisDO>) redisService.getValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currencyId);
        if (dealList == null || dealList.isEmpty()) {
            dealList = transactionDealRedisService.listTransactionDealRedis(50, currencyId);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dealList", dealList);

        resultJson.setCode(1);
        resultJson.setMessage("查询成功");
        resultJson.setData(jsonObject);

        return resultJson;
    }

    /** 获取挂单记录 */
    @RequestMapping(value = "/pend", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO pend(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();

        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        if (!StringUtil.isNotNull(currencyIdStr)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数获取错误");
            return resultJson;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if(transactionCurrency != null){

            if(transactionCurrency.getUpStatus() == 4){
                resultJson.setCode(5);
                resultJson.setMessage("该币种不在上线状态");
                return resultJson;
            }
        }

        List<TransactionPendOrderDTO> transactionPendOrderBuyList = null;
        transactionPendOrderBuyList = (List<TransactionPendOrderDTO>) redisService.getValue(RedisKeyConfig.BUY_KEY + currencyId);
        if (transactionPendOrderBuyList == null || transactionPendOrderBuyList.isEmpty()) {
            transactionPendOrderBuyList = transactionPendOrderService.listLatestRecords(1,currencyId,15);
        }

        List<TransactionPendOrderDTO> transactionPendOrderSellList = null;
        transactionPendOrderSellList = (List<TransactionPendOrderDTO>) redisService.getValue(RedisKeyConfig.SELL_KEY + currencyId);
        if (transactionPendOrderSellList == null || transactionPendOrderSellList.isEmpty()) {
            transactionPendOrderSellList = transactionPendOrderService.listLatestRecords(2,currencyId,15);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionPendOrderBuyList", transactionPendOrderBuyList);
        jsonObject.put("transactionPendOrderSellList", transactionPendOrderSellList);

        resultJson.setCode(1);
        resultJson.setMessage("查询成功");
        resultJson.setData(jsonObject);

        return resultJson;
    }

    /** 获取委托记录 */
    @RequestMapping(value = "/entrust.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO entrust(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        List<TransactionPendOrderVO> transactionPendOrderList = null;
        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数获取错误");
            return resultJson;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user != null) {
            transactionPendOrderList = transactionPendOrderService.listPendOrderForWeb(user.getUserId(),currencyId,0, 10);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionPendOrderList", transactionPendOrderList);

        resultJson.setCode(1);
        resultJson.setMessage("查询成功");
        resultJson.setData(jsonObject);

        return resultJson;

    }

    /** 获取交易相关价格（基准信息） */
    @RequestMapping(value = "/gainDealPrice", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO gainDealPrice(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数获取错误");
            return resultJson;
        }

        int currencyId;
        currencyId = Integer.parseInt(currencyIdStr);

        //获取币种基准信息
        StandardParameterVO standardParameter = transactionCurrencyService.listTransactionCurrencyAll(currencyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standardParameter", standardParameter);

        resultJson.setCode(1);
        resultJson.setMessage("查询成功");
        resultJson.setData(jsonObject);

        return resultJson;

    }

    /** 获取交易相关价格（用户资金信息） */
    @RequestMapping(value = "/userMessage", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO userMessage(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();

        //获取参数
        UserSessionBO user = UserWebInterceptor.getUser(request);
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        if (!StringUtil.isNotNull(currencyIdStr)) {
            resultJson.setCode(3);
            resultJson.setMessage("参数获取错误");
            return resultJson;
        }

        int currencyId;
        currencyId = Integer.parseInt(currencyIdStr);
        if (user != null) {
            userDealCapitalMessage = userService.countCheckUserAmountForTimer(user.getUserId(), currencyId);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userDealCapitalMessage", userDealCapitalMessage);

        resultJson.setCode(1);
        resultJson.setMessage("查询成功");
        resultJson.setData(jsonObject);
        return resultJson;
    }
}
