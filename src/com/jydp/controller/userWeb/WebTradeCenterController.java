package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.entity.VO.*;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.*;
import config.RedisKeyConfig;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * web2.0交易中心
 * @author njx
 **/
@Controller
@RequestMapping("/web/tradeCenter")
@Scope(value = "prototype")
public class WebTradeCenterController {
    /**
     * 交易币种
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * redis成交记录
     */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /**
     * 用户账号
     */
    @Autowired
    private IUserService userService;

    /**
     * redis服务
     */
    @Autowired
    private IRedisService redisService;

    /**
     * 挂单记录
     */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /**
     * 用户币数量
     */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /**
     * 匹配交易
     */
    @Autowired
    private ITradeCommonService tradeCommonService;

    /**
     * 展示交易页面
     **/
    @RequestMapping(value = "/show/{currencyIdStr}", method = RequestMethod.GET)
    public @ResponseBody JsonObjectBO show(HttpServletRequest request, HttpServletResponse response, @PathVariable String currencyIdStr) {
        //返回对象封装类
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        //数据封装的信息
        JSONObject jo = new JSONObject();

        //交易界面用户的币和钱信息
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();
        //挂单记录
        List<TransactionPendOrderVO> transactionPendOrderList = new ArrayList<TransactionPendOrderVO>();
        if (!StringUtil.isNotNull(currencyIdStr)) {
            List<TransactionCurrencyVO> transactionCurrency = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
            if (transactionCurrency == null || transactionCurrency.size() <= 0) {
                ResponseUtils.setResp(SystemMessageConfig.REDIRECT_TO_HOMEPAGE_CODE, SystemMessageConfig.REDIRECT_TO_HOMEPAGE_MESSAGE, null, jsonObjectBO);
                return jsonObjectBO;
            }
            currencyIdStr = transactionCurrency.get(0).getCurrencyId() + "";
        }
        //币种ID
        int currencyId = Integer.parseInt(currencyIdStr);

        //获取币种信息
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            ResponseUtils.setResp(SystemMessageConfig.REDIRECT_TO_HOMEPAGE_CODE, SystemMessageConfig.REDIRECT_TO_HOMEPAGE_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }
        //币种信息设置
        transactionCurrency.setBuyFee(transactionCurrency.getBuyFee() * 100);
        transactionCurrency.setSellFee(transactionCurrency.getSellFee() * 100);

        //获取用户交易中心相关资产信息
        UserSessionBO user = WebInterceptor.getUser(request);

        if (user != null) {
            //如果用户session在线
            //交易界面用户的币和钱信息设置
            userDealCapitalMessage = userService.countCheckUserAmountForTimer(user.getUserId(), currencyId);
            //挂单信息
            transactionPendOrderList = transactionPendOrderService.listPendOrderForWeb(user.getUserId(), currencyId, 0, 10);

            //获取用户没有的币种，创建该币种账户
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(), currencyId);
            if (userCurrencyNum == null) {
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                userCurrencyNumDO.setUserId(user.getUserId());
                userCurrencyNumDO.setCurrencyId(currencyId);
                userCurrencyNumDO.setCurrencyNumber(0);
                userCurrencyNumDO.setCurrencyNumberLock(0);
                userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

                List<UserCurrencyNumDO> userCurrencyNumDOList = new ArrayList<>();
                userCurrencyNumDOList.add(userCurrencyNumDO);
                userCurrencyNumService.insertUserCurrencyForWeb(userCurrencyNumDOList);
            }
            UserDO userDO = userService.getUserByUserId(user.getUserId());

            //用户密码是否输入过密码
            jo.put("userIsPwd", user.getIsPwd());
            //用户是否需要输入密码的状态
            jo.put("payPasswordStatus", userDO.getPayPasswordStatus());
        }else{
            //用户密码是否输入过密码
            jo.put("userIsPwd", 1);
            //用户是否需要输入密码的状态
            jo.put("payPasswordStatus", 1);
        }

        //获取币种基准信息
        StandardParameterVO standardParameter = transactionCurrencyService.listTransactionCurrencyAll(currencyId);

        //获取成交记录
        List<TransactionDealRedisDO> dealList = null;
        Object dealListStr = redisService.getValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currencyId);
        if (dealListStr != null && StringUtil.isNotNull(dealListStr.toString())) {
            dealList = (List<TransactionDealRedisDO>) dealListStr;
        }
        if (dealList == null || dealList.isEmpty()) {
            dealList = transactionDealRedisService.listTransactionDealRedis(50, transactionCurrency.getCurrencyId());
        }

        //获取买入挂单记录
        List<TransactionPendOrderDTO> transactionPendOrderBuyList = null;
        Object transactionPendOrderBuyListStr = redisService.getValue(RedisKeyConfig.BUY_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderBuyListStr != null && StringUtil.isNotNull(transactionPendOrderBuyListStr.toString())) {
            transactionPendOrderBuyList = (List<TransactionPendOrderDTO>) transactionPendOrderBuyListStr;
        }
        if (transactionPendOrderBuyList == null || transactionPendOrderBuyList.isEmpty()) {
            transactionPendOrderBuyList = transactionPendOrderService.listLatestRecords(1, transactionCurrency.getCurrencyId(), 15);
        }

        //卖出挂单记录
        List<TransactionPendOrderDTO> transactionPendOrderSellList = null;
        Object transactionPendOrderSellListStr = redisService.getValue(RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderSellListStr != null && StringUtil.isNotNull(transactionPendOrderSellListStr.toString())) {
            transactionPendOrderSellList = (List<TransactionPendOrderDTO>) transactionPendOrderSellListStr;
        }
        if (transactionPendOrderSellList == null || transactionPendOrderSellList.isEmpty()) {
            transactionPendOrderSellList = transactionPendOrderService.listLatestRecords(2, transactionCurrency.getCurrencyId(), 15);
        }

        jo.put("transactionPendOrderBuyList", transactionPendOrderBuyList);
        jo.put("transactionPendOrderSellList", transactionPendOrderSellList);
        jo.put("dealList", dealList);

        jo.put("transactionPendOrderList", transactionPendOrderList);
        jo.put("transactionCurrency", transactionCurrency);
        jo.put("standardParameter", standardParameter);
        jo.put("userDealCapitalMessage", userDealCapitalMessage);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;

    }

    /**
     * 卖出
     */
    @RequestMapping(value = "/sell.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO sell(HttpServletRequest request) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        UserSessionBO userSession = WebInterceptor.getUser(request);
        if (userSession == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_LOGININ_CODE, SystemMessageConfig.NOT_LOGININ_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        boolean fq = WebInterceptor.handleFrequent(request);
        if (fq) {
            ResponseUtils.setResp(SystemMessageConfig.OPERATING_FREQUENCY_CODE, SystemMessageConfig.OPERATING_FREQUENCY_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取参数
        String sellPriceStr = StringUtil.stringNullHandle(request.getParameter("sellPrice"));
        String sellNumStr = StringUtil.stringNullHandle(request.getParameter("sellNum"));
        String sellPwd = StringUtil.stringNullHandle(request.getParameter("sellPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        double sellPrice = 0;
        if (StringUtil.isNotNull(sellPriceStr)) {
            sellPrice = Double.parseDouble(sellPriceStr);
            sellPrice = NumberUtil.doubleFormat(sellPrice, 2);
        }

        double sellNum = 0;
        if (StringUtil.isNotNull(sellNumStr)) {
            sellNum = Double.parseDouble(sellNumStr);
            sellNum = NumberUtil.doubleFormat(sellNum, 4);
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if (user == null) {
            ResponseUtils.setResp(SystemMessageConfig.USER_NOT_EXIST_CODE, SystemMessageConfig.USER_NOT_EXIST_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (user.getAccountStatus() != 1) {
            ResponseUtils.setResp(SystemMessageConfig.ACCOUNT_DISABLED_CODE, SystemMessageConfig.ACCOUNT_DISABLED_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_CODE, SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (transactionCurrency.getUpStatus() == 4) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_OFFLINE_CODE, SystemMessageConfig.CURRENCY_OFFLINE_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (transactionCurrency.getPaymentType() != 1) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_NOT_TRADED_CODE, SystemMessageConfig.CURRENCY_NOT_TRADED_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取用户币信息
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(),
                currencyId);
        Boolean insertBoo = true;
        if (userCurrencyNum == null) {
            UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
            userCurrencyNumDO.setUserId(user.getUserId());
            userCurrencyNumDO.setCurrencyId(currencyId);
            userCurrencyNumDO.setCurrencyNumber(0);
            userCurrencyNumDO.setCurrencyNumberLock(0);
            userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

            insertBoo = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
        }
        if (!insertBoo) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //判断交易时间限制
        boolean timeBoo = DateUtil.isTradeTime();
        if (timeBoo) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_IN_TRADED_TIME_CODE, SystemMessageConfig.NOT_IN_TRADED_TIME_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //交易数量限制
        if (sellNum <= 0) {
            ResponseUtils.setResp(SystemMessageConfig.TRADE_NUM_WRONG_CODE, SystemMessageConfig.TRADE_NUM_WRONG_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //交易价格限制
        if (sellPrice <= 0) {
            ResponseUtils.setResp(SystemMessageConfig.TRADE_PRICE_WRONG_CODE, SystemMessageConfig.TRADE_PRICE_WRONG_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //判断是否需要验证交易密码以及验证交易密码
        int isPwd = userSession.getIsPwd();
        int payPasswordStatus = user.getPayPasswordStatus();
        jo.put("isPwd", isPwd);
        jo.put("payPasswordStatus", payPasswordStatus);
        if (payPasswordStatus == 1 || (payPasswordStatus == 2 && isPwd == 1) || StringUtil.isNotNull(sellPwd)) {
            sellPwd = Base64Util.decode(sellPwd);
            sellPwd = MD5Util.toMd5(sellPwd);
            boolean checkResult = userService.validateUserPay(user.getUserAccount(), sellPwd);
            if (!checkResult) {
                //重置交易密码状态
                userService.updateUserPayPasswordStatus(user.getUserId(), 1);
                userSession.setIsPwd(1);
                request.getSession().setAttribute("userSession", userSession);
                jo.put("isPwd", 1);
                jo.put("payPasswordStatus", 1);
                ResponseUtils.setResp(SystemMessageConfig.TRADE_PASS_WRONG_CODE, SystemMessageConfig.TRADE_PASS_WRONG_MESSAGE, jo, jsonObjectBO);
                return jsonObjectBO;
            } else {
                userSession.setIsPwd(2);
                request.getSession().setAttribute("userSession", userSession);
                jo.put("isPwd", 2);
            }
        }

        if (userCurrencyNum.getCurrencyNumber() < sellNum) {
            ResponseUtils.setResp(SystemMessageConfig.COIN_NOT_ENOUGH_CODE, SystemMessageConfig.COIN_NOT_ENOUGH_MESSAGE, jo, jsonObjectBO);
            return jsonObjectBO;
        }

        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 2, currencyId,
                transactionCurrency.getCurrencyName(), 0, sellPrice, sellNum, 0);

        if (transactionPendOrder == null) {
            ResponseUtils.setResp(SystemMessageConfig.PEND_FAILURE_CODE, SystemMessageConfig.PEND_FAILURE_MESSAGE, jo, jsonObjectBO);
            return jsonObjectBO;
        }

        RabbitUtils.trdeOrder(transactionPendOrder);//放入队列
        ResponseUtils.setResp(SystemMessageConfig.PEND_SUCCESS_CODE, SystemMessageConfig.PEND_SUCCESS_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;

    }

    /**
     * 买入
     */
    @RequestMapping(value = "/buy.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO buy(HttpServletRequest request) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        UserSessionBO userSession = WebInterceptor.getUser(request);
        if (userSession == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_LOGININ_CODE, SystemMessageConfig.NOT_LOGININ_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        boolean fq = WebInterceptor.handleFrequent(request);
        if (fq) {
            ResponseUtils.setResp(SystemMessageConfig.OPERATING_FREQUENCY_CODE, SystemMessageConfig.OPERATING_FREQUENCY_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取参数
        String buyPriceStr = StringUtil.stringNullHandle(request.getParameter("buyPrice"));
        String buyNumStr = StringUtil.stringNullHandle(request.getParameter("buyNum"));
        String buyPwd = StringUtil.stringNullHandle(request.getParameter("buyPwd"));
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));

        double buyPrice = 0;
        if (StringUtil.isNotNull(buyPriceStr)) {
            buyPrice = Double.parseDouble(buyPriceStr);
            buyPrice = NumberUtil.doubleFormat(buyPrice, 2);
        }

        double buyNum = 0;
        if (StringUtil.isNotNull(buyNumStr)) {
            buyNum = Double.parseDouble(buyNumStr);
            buyNum = NumberUtil.doubleFormat(buyNum, 4);
        }

        int currencyId = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }

        //获取用户信息
        UserDO user = userService.getUserByUserId(userSession.getUserId());
        if (user == null) {
            ResponseUtils.setResp(SystemMessageConfig.USER_NOT_EXIST_CODE, SystemMessageConfig.USER_NOT_EXIST_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (user.getAccountStatus() != 1) {
            ResponseUtils.setResp(SystemMessageConfig.ACCOUNT_DISABLED_CODE, SystemMessageConfig.ACCOUNT_DISABLED_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_CODE, SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (transactionCurrency.getUpStatus() == 4) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_OFFLINE_CODE, SystemMessageConfig.CURRENCY_OFFLINE_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (transactionCurrency.getPaymentType() != 1) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_NOT_TRADED_CODE, SystemMessageConfig.CURRENCY_NOT_TRADED_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取用户币信息
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(user.getUserId(),
                currencyId);
        Boolean insertBoo = true;
        if (userCurrencyNum == null) {
            UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
            userCurrencyNumDO.setUserId(user.getUserId());
            userCurrencyNumDO.setCurrencyId(currencyId);
            userCurrencyNumDO.setCurrencyNumber(0);
            userCurrencyNumDO.setCurrencyNumberLock(0);
            userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

            insertBoo = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
        }
        if (!insertBoo) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //判断交易时间限制
        boolean timeBoo = DateUtil.isTradeTime();
        if (timeBoo) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_IN_TRADED_TIME_CODE, SystemMessageConfig.NOT_IN_TRADED_TIME_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //交易数量限制
        if (buyNum <= 0) {
            ResponseUtils.setResp(SystemMessageConfig.TRADE_NUM_WRONG_CODE, SystemMessageConfig.TRADE_NUM_WRONG_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //交易价格限制
        if (buyPrice <= 0) {
            ResponseUtils.setResp(SystemMessageConfig.TRADE_PRICE_WRONG_CODE, SystemMessageConfig.TRADE_PRICE_WRONG_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //判断是否需要验证交易密码以及验证交易密码
        int isPwd = userSession.getIsPwd();
        int payPasswordStatus = user.getPayPasswordStatus();
        jo.put("isPwd", isPwd);
        jo.put("payPasswordStatus", payPasswordStatus);

        if (payPasswordStatus == 1 || (payPasswordStatus == 2 && isPwd == 1) || StringUtil.isNotNull(buyPwd)) {
            buyPwd = Base64Util.decode(buyPwd);
            buyPwd = MD5Util.toMd5(buyPwd);
            boolean checkResult = userService.validateUserPay(user.getUserAccount(), buyPwd);
            if (!checkResult) {
                //重置交易密码状态
                userService.updateUserPayPasswordStatus(user.getUserId(), 1);
                userSession.setIsPwd(1);
                request.getSession().setAttribute("userSession", userSession);
                jo.put("isPwd", 1);
                jo.put("payPasswordStatus", 1);
                ResponseUtils.setResp(SystemMessageConfig.TRADE_PASS_WRONG_CODE, SystemMessageConfig.TRADE_PASS_WRONG_MESSAGE, jo, jsonObjectBO);
                return jsonObjectBO;
            } else {
                userSession.setIsPwd(2);
                request.getSession().setAttribute("userSession", userSession);
                jo.put("isPwd", 2);
            }
        }

        //计算手续费及总价
        double buyFee = transactionCurrency.getBuyFee();
        double sumPrice = NumberUtil.doubleUpFormat(BigDecimalUtil.mul(BigDecimalUtil.mul(buyNum, buyPrice), BigDecimalUtil.add(1, buyFee)), 8);

        if (user.getUserBalance() < sumPrice) {
            ResponseUtils.setResp(SystemMessageConfig.MONEY_NOT_ENOUGH_CODE, SystemMessageConfig.MONEY_NOT_ENOUGH_MESSAGE, jo, jsonObjectBO);
            return jsonObjectBO;
        }

        //挂单
        TransactionPendOrderDO transactionPendOrder = transactionPendOrderService.insertPendOrder(user.getUserId(), 1, currencyId,
                transactionCurrency.getCurrencyName(), buyFee, buyPrice, buyNum, sumPrice);

        if (transactionPendOrder == null) {
            ResponseUtils.setResp(SystemMessageConfig.PEND_FAILURE_CODE, SystemMessageConfig.PEND_FAILURE_MESSAGE, jo, jsonObjectBO);
            return jsonObjectBO;
        }
        //匹配交易
        RabbitUtils.trdeOrder(transactionPendOrder);//放入队列
        ResponseUtils.setResp(SystemMessageConfig.PEND_SUCCESS_CODE, SystemMessageConfig.PEND_SUCCESS_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 获取成交记录
     */
    @RequestMapping(value = "/deal", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO deal(HttpServletRequest request, @RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getCurrencyId());
        if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }
        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        //获取币种信息
        int updatetus = transactionCurrencyService.getCurrencyUpstatusByCurrencyId(currencyId);

        if (updatetus == 0) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_CODE, SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }
        if (updatetus == 4) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_OFFLINE_CODE, SystemMessageConfig.CURRENCY_OFFLINE_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        List<TransactionDealRedisDO> dealList = null;
        Object dealListStr = redisService.getValue(RedisKeyConfig.CURRENCY_DEAL_KEY + currencyId);
        if (dealListStr != null && StringUtil.isNotNull(dealListStr.toString())) {
            dealList = (List<TransactionDealRedisDO>) dealListStr;
        }
        if (dealList == null || dealList.isEmpty()) {
            dealList = transactionDealRedisService.listTransactionDealRedis(50, currencyId);
        }

        jo.put("dealList", dealList);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 获取挂单记录
     */
    @RequestMapping(value = "/pend", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO pend(HttpServletRequest request, @RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();
        //获取参数
       String currencyIdStr = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getCurrencyId());
       if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);

        //获取币种信息
        TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(currencyId);
        if (transactionCurrency == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_CODE, SystemMessageConfig.NOT_HAVE_CURRENCY_INFO_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }
        if (transactionCurrency.getUpStatus() == 4) {
            ResponseUtils.setResp(SystemMessageConfig.CURRENCY_OFFLINE_CODE, SystemMessageConfig.CURRENCY_OFFLINE_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        List<TransactionPendOrderDTO> transactionPendOrderBuyList = null;
        Object transactionPendOrderBuyListStr = redisService.getValue(RedisKeyConfig.BUY_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderBuyListStr != null && StringUtil.isNotNull(transactionPendOrderBuyListStr.toString())) {
            transactionPendOrderBuyList = (List<TransactionPendOrderDTO>) transactionPendOrderBuyListStr;
        }
        if (transactionPendOrderBuyList == null || transactionPendOrderBuyList.isEmpty()) {
            transactionPendOrderBuyList = transactionPendOrderService.listLatestRecords(1, transactionCurrency.getCurrencyId(), 15);
        }

        List<TransactionPendOrderDTO> transactionPendOrderSellList = null;
        Object transactionPendOrderSellListStr = redisService.getValue(RedisKeyConfig.SELL_KEY + transactionCurrency.getCurrencyId());
        if (transactionPendOrderSellListStr != null && StringUtil.isNotNull(transactionPendOrderSellListStr.toString())) {
            transactionPendOrderSellList = (List<TransactionPendOrderDTO>) transactionPendOrderSellListStr;
        }
        if (transactionPendOrderSellList == null || transactionPendOrderSellList.isEmpty()) {
            transactionPendOrderSellList = transactionPendOrderService.listLatestRecords(2, transactionCurrency.getCurrencyId(), 15);
        }

        jo.put("transactionPendOrderBuyList", transactionPendOrderBuyList);
        jo.put("transactionPendOrderSellList", transactionPendOrderSellList);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 获取委托记录
     */
    @RequestMapping(value = "/entrust.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO entrust(HttpServletRequest request, @RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();
        List<TransactionPendOrderVO> transactionPendOrderList = null;
        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getCurrencyId());
        if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        UserSessionBO user = WebInterceptor.getUser(request);
        if (user != null) {
            transactionPendOrderList = transactionPendOrderService.listPendOrderForWeb(user.getUserId(), currencyId, 0, 10);
        }
        jo.put("transactionPendOrderList", transactionPendOrderList);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 获取交易相关价格（基准信息）
     */
    @RequestMapping(value = "/gainDealPrice", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO gainDealPrice(HttpServletRequest request, @RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();
        //获取参数
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter(transactionCurrencyParametersDO.getCurrencyId()));
        if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        //获取币种基准信息
        StandardParameterVO standardParameter = transactionCurrencyService.listTransactionCurrencyAll(currencyId);
        jo.put("standardParameter", standardParameter);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 获取交易相关价格（用户资金信息）
     */
    @RequestMapping(value = "/userMessage", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO userMessage(HttpServletRequest request, @RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();
        //获取参数
        UserSessionBO user = WebInterceptor.getUser(request);
        String currencyIdStr = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getCurrencyId());
        if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        if (user != null) {
            userDealCapitalMessage = userService.countCheckUserAmountForTimer(user.getUserId(), currencyId);
        }
        jo.put("userDealCapitalMessage", userDealCapitalMessage);

        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * 记住密码提示
     */
    @RequestMapping(value = "/rememberPwd.htm", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO rememberPwd(HttpServletRequest request,@RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersDO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        UserSessionBO user = WebInterceptor.getUser(request);
        if (user == null) {
            ResponseUtils.setResp(SystemMessageConfig.NOT_LOGININ_CODE, SystemMessageConfig.NOT_LOGININ_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //获取参数
        String rememberPwd = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getRememberPwd());
        rememberPwd = Base64Util.decode(rememberPwd);
        String payPasswordStatusStr = StringUtil.stringNullHandle(transactionCurrencyParametersDO.getPayPasswordStatus());
        if (!StringUtil.isNotNull(rememberPwd)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int payPasswordStatus = 1;
        if (StringUtil.isNotNull(payPasswordStatusStr)) {
            payPasswordStatus = Integer.parseInt(payPasswordStatusStr);
        }

        //验证交易密码
        rememberPwd = MD5Util.toMd5(rememberPwd);
        boolean checkResult = userService.validateUserPay(user.getUserAccount(), rememberPwd);
        if (!checkResult) {
            ResponseUtils.setResp(SystemMessageConfig.TRADE_PASS_WRONG_CODE, SystemMessageConfig.TRADE_PASS_WRONG_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        //修改交易密码状态
        boolean updateBoo = userService.updateUserPayPasswordStatus(user.getUserId(), payPasswordStatus);
        if (!updateBoo) {
            ResponseUtils.setResp(SystemMessageConfig.MODIFY_FAILD_CODE, SystemMessageConfig.MODIFY_FAILD_MESSAGE, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (payPasswordStatus == 2) {
            user.setIsPwd(2);
            request.getSession().setAttribute("userSession", user);
            jo.put("userIsPwd", 2);
            jo.put("payPasswordStatus", 2);
        } else if (payPasswordStatus == 1) {
            user.setIsPwd(1);
            request.getSession().setAttribute("userSession", user);
            jo.put("userIsPwd", 1);
            jo.put("payPasswordStatus", 1);
        }

        ResponseUtils.setResp(SystemMessageConfig.MODIFY_SUCCESS_CODE, SystemMessageConfig.MODIFY_SUCCESS_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

    /**
     * k线图参数获取
     */
    @RequestMapping(value = "/gainGraphData", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO gainGraphData(HttpServletRequest request,@RequestBody TransactionCurrencyParametersVO transactionCurrencyParametersVO) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();

        String currencyIdStr = StringUtil.stringNullHandle(transactionCurrencyParametersVO.getCurrencyId());
        String node = StringUtil.stringNullHandle(transactionCurrencyParametersVO.getNode());
        if (!StringUtil.isNotNull(currencyIdStr)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        if (!StringUtil.isNotNull(node)) {
            ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR, SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR, null, jsonObjectBO);
            return jsonObjectBO;
        }

        int currencyId = 0;
        currencyId = Integer.parseInt(currencyIdStr);
        JSONArray transactionGraphList = transactionDealRedisService.gainGraphDataWithNode(currencyId, node);
        jo.put("transactionGraphList", transactionGraphList);
        ResponseUtils.setResp(SystemMessageConfig.SUCCESS_OPT_CODE, SystemMessageConfig.SUCCESS_OPT_MESSAGE, jo, jsonObjectBO);
        return jsonObjectBO;
    }

}
